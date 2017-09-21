/**
 * 
 */
package com.cooksys.twitterclone.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.twitterclone.dto.ContextDto;
import com.cooksys.twitterclone.dto.CredentialsDto;
import com.cooksys.twitterclone.dto.HashtagGetDto;
import com.cooksys.twitterclone.dto.TweetGetDto;
import com.cooksys.twitterclone.dto.TweetSaveDto;
import com.cooksys.twitterclone.dto.UserGetDto;
import com.cooksys.twitterclone.entity.ContextEntity;
import com.cooksys.twitterclone.entity.TweetEntity;
import com.cooksys.twitterclone.entity.UserEntity;
import com.cooksys.twitterclone.entity.embeddable.CredentialsEmbeddable;
import com.cooksys.twitterclone.mapper.CredentialsMapper;
import com.cooksys.twitterclone.mapper.HashtagMapper;
import com.cooksys.twitterclone.mapper.TweetMapper;
import com.cooksys.twitterclone.mapper.UserMapper;
import com.cooksys.twitterclone.repository.ContextJpaRepository;
import com.cooksys.twitterclone.repository.TweetJpaRepository;
import com.cooksys.twitterclone.utilities.Utilities;

/**
 * @author Greg Hill
 *
 */
@Service
public class TweetService {
	
	private TweetJpaRepository tweetJpaRepository;
	private ContextJpaRepository contextJpaRepository;
	private TweetMapper tweetMapper;
	private CredentialsMapper credentialsMapper;
	private HashtagMapper hashtagMapper;
	private UserMapper userMapper;
	private ValidateService validateService;
	private UserService userService;
	
	private final TweetGetDto ERROR = null;

	public TweetService(TweetJpaRepository tweetJpaRespository, ContextJpaRepository contextJpaRepository, TweetMapper tweetMapper, 
			CredentialsMapper credentialsMapper, HashtagMapper hashtagMapper, UserMapper userMapper, ValidateService validateService, 
			UserService userService) {
		this.tweetJpaRepository = tweetJpaRepository;
		this.contextJpaRepository = contextJpaRepository;
		this.tweetMapper = tweetMapper;
		this.credentialsMapper = credentialsMapper;
		this.hashtagMapper = hashtagMapper;
		this.userMapper = userMapper;
		this.validateService = validateService;
		this.userService = userService;
	}
	
	public TweetEntity pullTweet(Integer id) {
		return validateService.pullTweet(id);
	}

	public Set<TweetGetDto> getTweets() {
		return tweetMapper.toDto(tweetJpaRepository.findByActive(true)).descendingSet();
	}

	public TweetGetDto postTweet(TweetSaveDto tweetSaveDto) {
		TweetEntity tweet = tweetMapper.fromDtoSave(tweetSaveDto);
		CredentialsEmbeddable credentials = credentialsMapper.fromDto(tweetSaveDto.getCredentials());
		
		// Ensures that the fields for the new tweet are valid
		if(!validateService.validTweetFields(tweet) || !validateService.validateCredentials(credentials)) {
			return ERROR;
		}
		
		tweet.setAuthor(validateService.pullUser(credentials.getUsername()));
		tweet.setPosted(Utilities.currentTime());
		tweet = storeTagsAndMentions(tweet);
		
		ContextEntity context = new ContextEntity();
		tweet.setContext(context);
		
		tweetJpaRepository.save(tweet);
		contextJpaRepository.save(context);
		
		return tweetMapper.toDtoGet(tweet);
	}

	public TweetGetDto getTweet(Integer id) {
		if(validateService.getTweetExists(id)) {
			return tweetMapper.toDtoGet(pullTweet(id));
		} else {
			return ERROR;
		}
	}

	public TweetGetDto deleteTweet(Integer id, CredentialsDto credentialsDto) {
		CredentialsEmbeddable credentials = credentialsMapper.fromDto(credentialsDto);

		// Checks if the tweet exists and if the credentials are correct
		if(!validateService.getTweetExists(id) || 
			!validateService.validateCredentials(credentials)) {
			return ERROR;
		}
		
		TweetEntity tweet = pullTweet(id);
		tweet.setActive(false);
		
		tweetJpaRepository.save(tweet);
		return tweetMapper.toDtoGet(tweet);
	}

	public TweetGetDto likeTweet(Integer id, CredentialsDto credentialsDto) {
		CredentialsEmbeddable credentials = credentialsMapper.fromDto(credentialsDto);

		// Checks if the tweet and user exist and if the credentials are correct
		if(!validateService.getTweetExists(id) || 
			!validateService.getUsernameExists(credentials.getUsername()) ||
			!validateService.validateCredentials(credentials)) {
			return ERROR;
		}

		UserEntity user = validateService.pullUser(credentials.getUsername());
		
		TweetEntity tweetToLike = pullTweet(id);
		Set<UserEntity> likes = tweetToLike.getLikes();
		
		// Checks if the user already likes this tweet
		if(likes.contains(user)) {
			return ERROR;
		}
		
		likes.add(user);
		tweetJpaRepository.save(tweetToLike);
		return tweetMapper.toDtoGet(tweetToLike);
	}

	public TweetGetDto replyToTweet(Integer id, TweetSaveDto tweetSaveDto) {
		TweetEntity tweet = tweetMapper.fromDtoSave(tweetSaveDto);
		CredentialsEmbeddable credentials = credentialsMapper.fromDto(tweetSaveDto.getCredentials());
		
		// Ensures that the fields for the new tweet are valid
		if(!validateService.validTweetFields(tweet) || !validateService.validateCredentials(credentials)) {
			return ERROR;
		}
		
		tweet.setAuthor(validateService.pullUser(credentials.getUsername()));
		tweet.setPosted(Utilities.currentTime());
		tweet.setInReplyTo(pullTweet(id));
		tweet = joinContext(tweet, id);
		tweet = storeTagsAndMentions(tweet);
		
		tweetJpaRepository.save(tweet);
		
		return tweetMapper.toDtoGet(tweet);
	}

	public TweetGetDto repostOfTweet(Integer id, CredentialsDto credentialsDto) {
		TweetEntity tweet = new TweetEntity();
		CredentialsEmbeddable credentials = credentialsMapper.fromDto(credentialsDto);
		
		// Ensures that the credentials match a valid user and the post requested exists
		if(!validateService.validateCredentials(credentials) || !validateService.getTweetExists(id)) {
			return ERROR;
		}
		
		tweet.setAuthor(validateService.pullUser(credentials.getUsername()));
		tweet.setPosted(Utilities.currentTime());
		tweet.setRepostOf(pullTweet(id));
		tweet = joinContext(tweet, id);
		tweet = storeTagsAndMentions(tweet);
		
		tweetJpaRepository.save(tweet);
		return tweetMapper.toDtoGet(tweet);
	}

	public Set<HashtagGetDto> getTweetTags(Integer id) {
		return hashtagMapper.toDto(pullTweet(id).getHashtags());
	}

	public Set<UserGetDto> getTweetLikes(Integer id) {
		return userMapper.toDto(pullTweet(id)
				.getLikes()
				.stream()
				.filter(user -> user.getActive().equals(true))
				.collect(Collectors.toCollection(TreeSet::new)));
	}

	public ContextDto getContext(Integer id) {
		TweetEntity primaryTweet = pullTweet(id);
		TreeSet<TweetEntity> before = new TreeSet<TweetEntity>();
		TreeSet<TweetEntity> after = new TreeSet<TweetEntity>();
		
		primaryTweet
			.getContext()
			.getFullTweetContext()
			.stream()
			.forEach(tweet -> {
				if(tweet.getActive().equals(true)) {
					if(tweet.getId() < primaryTweet.getId()) {
						before.add(tweet);
					} else if(tweet.getId() > primaryTweet.getId()) {
						after.add(tweet);
					}
				}
			});
		return new ContextDto(tweetMapper.toDtoGet(primaryTweet), 
					tweetMapper.toDto(before), 
					tweetMapper.toDto(before));
	}

	public Set<TweetGetDto> getReplies(Integer id) {
		return tweetMapper.toDto(tweetJpaRepository
				.findByInReplyToIs(id)
				.stream()
				.filter(tweet -> tweet.getActive().equals(true))
				.collect(Collectors.toCollection(TreeSet::new)));
	}

	public Set<TweetGetDto> getReposts(Integer id) {
		return tweetMapper.toDto(tweetJpaRepository
				.findByRepostOfIs(id)
				.stream()
				.filter(tweet -> tweet.getActive().equals(true))
				.collect(Collectors.toCollection(TreeSet::new)));
	}

	public Set<UserGetDto> getMentions(Integer id) {
		return userMapper.toDto(pullTweet(id)
				.getMentionedUsers()
				.stream()
				.filter(user -> user.getActive().equals(true))
				.collect(Collectors.toCollection(TreeSet::new)));
	}
	
	private TweetEntity joinContext(TweetEntity tweet, Integer id) {
		TweetEntity parent = pullTweet(id);
		
		if(!parent.getInReplyTo().equals(null)) {
			return joinContext(tweet, parent.getInReplyTo().getId());
		} else if(!parent.getRepostOf().equals(null)) {
			return joinContext(tweet, parent.getRepostOf().getId());
		} else {
			tweet.setContext(parent.getContext());
			return tweet;
		}
	}

	private TweetEntity storeTagsAndMentions(TweetEntity tweet){
		List<String> content = new ArrayList<String>(Arrays.asList(tweet.getContent().split("")));
		if(content.contains(new String("@"))) {
			StringBuffer username = new StringBuffer("");
			Boolean newUserTrigger = false;
			
			for(String character : content) {
				if(character.equals(" ") && newUserTrigger) {
					newUserTrigger = false;
					if(validateService.getUsernameExists(username.toString())) {
						tweet.getMentionedUsers().add(userService.pullUser(username.toString()));
					}
					username = new StringBuffer("");
				}
				
				if(newUserTrigger) {
					username.append(character);
				}
				
				if(character.equals("@")) {
					newUserTrigger = true;
				}
			}
			
			if(newUserTrigger) {
				if(validateService.getUsernameExists(username.toString())) {
					tweet.getMentionedUsers().add(userService.pullUser(username.toString()));
				}
			}
			
		}
		
		if(content.contains(new String("#"))) {
			StringBuffer hashtag = new StringBuffer("");
			Boolean newHashtagTrigger = false;
			
			for(String character : content) {
				if(character.equals(" ") && newHashtagTrigger) {
					newHashtagTrigger = false;
					if(validateService.getTagExists(hashtag.toString())) {
						tweet.getHashtags().add(validateService.pullTag(hashtag.toString()));
					}
					hashtag = new StringBuffer("");
				}
				
				if(newHashtagTrigger) {
					hashtag.append(character);
				}
				
				if(character.equals("@")) {
					newHashtagTrigger = true;
				}
			}
			
			if(newHashtagTrigger) {
				if(validateService.getTagExists(hashtag.toString())) {
					tweet.getHashtags().add(validateService.pullTag(hashtag.toString()));
				}
			}
		}
		
		return tweet;
	}
	
}
