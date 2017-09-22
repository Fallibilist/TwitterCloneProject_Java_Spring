/**
 * 
 */
package com.cooksys.twitterclone.service;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.twitterclone.dto.CredentialsDto;
import com.cooksys.twitterclone.dto.TweetGetDto;
import com.cooksys.twitterclone.dto.UserGetDto;
import com.cooksys.twitterclone.dto.UserSaveDto;
import com.cooksys.twitterclone.entity.TweetEntity;
import com.cooksys.twitterclone.entity.UserEntity;
import com.cooksys.twitterclone.entity.embeddable.CredentialsEmbeddable;
import com.cooksys.twitterclone.entity.embeddable.ProfileEmbeddable;
import com.cooksys.twitterclone.mapper.CredentialsMapper;
import com.cooksys.twitterclone.mapper.ProfileMapper;
import com.cooksys.twitterclone.mapper.TweetMapper;
import com.cooksys.twitterclone.mapper.UserMapper;
import com.cooksys.twitterclone.repository.UserJpaRepository;
import com.cooksys.twitterclone.utilities.Utilities;

/**
 * @author Greg Hill
 *
 */
@Service
public class UserService {

	private UserJpaRepository userJpaRepository;
	
	private UserMapper userMapper;
	
	private CredentialsMapper credentialsMapper;
	
	private ProfileMapper profileMapper;
	
	private TweetMapper tweetMapper;
	
	private ValidateService validateService;
	
	private final UserGetDto ERROR = null;

	/**
	 * Constructor injecting repositories, mappers, and services
	 * @param userJpaRepository
	 * @param tweetJpaRepository
	 * @param userMapper
	 * @param credentialsMapper
	 * @param profileMapper
	 * @param tweetMapper
	 * @param validateService
	 */
	public UserService(UserJpaRepository userJpaRepository, UserMapper userMapper, CredentialsMapper credentialsMapper,
			ProfileMapper profileMapper, TweetMapper tweetMapper, ValidateService validateService) {
		this.userJpaRepository = userJpaRepository;
		this.userMapper = userMapper;
		this.credentialsMapper = credentialsMapper;
		this.profileMapper = profileMapper;
		this.tweetMapper = tweetMapper;
		this.validateService = validateService;
	}
	
	/**
	 * @param username
	 * @return a user by username
	 */
	public UserEntity pullUser(String username) {
		return validateService.pullUser(username);
	}

	/**
	 * @return a set of all users that are active
	 */
	public TreeSet<UserGetDto> getUsers() {
		return userMapper.toDto(userJpaRepository.findByActive(true));
	}

	/**
	 * @param userSaveDto
	 * @return the created user
	 */
	public UserGetDto postUser(UserSaveDto userSaveDto) {
		UserEntity user = userMapper.fromDtoSave(userSaveDto, profileMapper, credentialsMapper);
		CredentialsEmbeddable credentials = user.getCredentials();
		
		if(!validateService.validUserFields(user)) {
			return ERROR;
		}
		
		if(validateService.getUsernameAvailable(credentials.getUsername())) {
			user.setJoined(Utilities.currentTime());
			userJpaRepository.save(user);
			return userMapper.toDtoGet(user);
		} else if(validateService.getUsernameInactive(credentials.getUsername())) {
			if(validateService.validateCredentials(credentials)) {
				user = pullUser(credentials.getUsername());
				user.setActive(true);
				
				Set<TweetEntity> deletedTweets = user.getDeletedTweets();
				
				user.getTweets().forEach(tweet -> {
					if(!deletedTweets.contains(tweet)) {
						tweet.setActive(true);
					}
				});
				
				userJpaRepository.save(user);
				return userMapper.toDtoGet(user);
			} else {
				return ERROR;
			}
		} else {
			return ERROR;
		}
	}

	/**
	 * @param username
	 * @return retrieves a user is it is active
	 */
	public UserGetDto getUser(String username) {
		if(validateService.getUsernameExists(username)) {
			return userMapper.toDtoGet(pullUser(username));
		} else {
			return ERROR;
		}
	}

	/**
	 * @param username
	 * @param userSaveDto
	 * @return a modified user
	 */
	public UserGetDto patchUser(String username, UserSaveDto userSaveDto) {
		UserEntity user = userMapper.fromDtoSave(userSaveDto, profileMapper, credentialsMapper);
		CredentialsEmbeddable newCredentials = user.getCredentials();
		ProfileEmbeddable newProfile = user.getProfile();

		if(!validateService.getUsernameExists(username) || 
			!validateService.validateCredentials(newCredentials) ||
			!username.equals(newCredentials.getUsername())) {
			return ERROR;
		}
		
		user = pullUser(username);
		
		if(newProfile.getEmail() != null) {
			user.getProfile().setEmail(newProfile.getEmail());;
		}
		
		if(newProfile.getFirstName() != null) {
			user.getProfile().setFirstName(newProfile.getFirstName());;
		}
		
		if(newProfile.getLastName() != null) {
			user.getProfile().setLastName(newProfile.getLastName());;
		}
		
		if(newProfile.getPhone() != null) {
			user.getProfile().setPhone(newProfile.getPhone());;
		}
		
		userJpaRepository.save(user);
		return userMapper.toDtoGet(user);
	}

	/**
	 * @param username
	 * @param userSaveDto
	 * @return a user whose profile has been overwritten by input data
	 */
	public UserGetDto putUser(String username, UserSaveDto userSaveDto) {
		UserEntity user = userMapper.fromDtoSave(userSaveDto, profileMapper, credentialsMapper);
		CredentialsEmbeddable newCredentials = user.getCredentials();
		ProfileEmbeddable newProfile = user.getProfile();

		if(!validateService.getUsernameExists(username) || 
			!validateService.validateCredentials(newCredentials) ||
			!username.equals(newCredentials.getUsername()) ||
			user.getProfile().getEmail() == null) {
			return ERROR;
		}
		
		user = pullUser(username);
		user.setProfile(newProfile);
		
		userJpaRepository.save(user);
		return userMapper.toDtoGet(user);
	}

	/**
	 * @param username
	 * @param credentialsDto
	 * @return the deleted user
	 */
	public UserGetDto deleteUser(String username, CredentialsDto credentialsDto) {
		CredentialsEmbeddable credentials = credentialsMapper.fromDto(credentialsDto);

		if(!validateService.getUsernameExists(username) || 
			!validateService.validateCredentials(credentials) ||
			!username.equals(credentials.getUsername())) {
			return ERROR;
		}
		
		UserEntity user = pullUser(username);
		user.setActive(false);
		
		user.getTweets()
			.forEach(tweet -> {
				if(!tweet.getActive()) {
					tweet.setDeactivatedUser(user);
				}
			});
		
		user.getTweets()
			.forEach(tweet -> tweet.setActive(false));
		
		userJpaRepository.save(user);
		return userMapper.toDtoGet(user);
	}

	/**
	 * @param username
	 * @param credentialsDto
	 * @return the user that was followed
	 */
	public UserGetDto followUser(String username, CredentialsDto credentialsDto) {
		CredentialsEmbeddable credentials = credentialsMapper.fromDto(credentialsDto);

		if(!validateService.getUsernameExists(username) || 
			!validateService.getUsernameExists(credentials.getUsername()) ||
			!validateService.validateCredentials(credentials)) {
			return ERROR;
		}

		UserEntity userToFollow = pullUser(username);
		
		UserEntity user = pullUser(credentials.getUsername());
		Set<UserEntity> following = user.getFollowing();
		
		if(following.contains(userToFollow)) {
			return ERROR;
		}
		
		following.add(userToFollow);
		userJpaRepository.save(user);
		return userMapper.toDtoGet(user);
	}

	/**
	 * @param username
	 * @param credentialsDto
	 * @return the user that was unfollowed
	 */
	public UserGetDto unfollowUser(String username, CredentialsDto credentialsDto) {
		CredentialsEmbeddable credentials = credentialsMapper.fromDto(credentialsDto);

		if(!validateService.getUsernameExists(username) || 
			!validateService.getUsernameExists(credentials.getUsername()) ||
			!validateService.validateCredentials(credentials)) {
			return ERROR;
		}

		UserEntity userToUnfollow = pullUser(username);
		
		UserEntity user = pullUser(credentials.getUsername());
		Set<UserEntity> following = user.getFollowing();
		
		if(!following.contains(userToUnfollow)) {
			return ERROR;
		}
		
		following.remove(userToUnfollow);
		userJpaRepository.save(user);
		return userMapper.toDtoGet(user);
	}

	/**
	 * @param username
	 * @return a set of all of the followers of the given user
	 */
	public TreeSet<UserGetDto> getFollowers(String username) {
		return userMapper
				.toDto(pullUser(username)
				.getFollowers()
				.stream()
				.filter(follower -> follower.getActive() == true)
				.collect(Collectors.toCollection(TreeSet::new)));
	}

	/**
	 * @param username
	 * @return a set of all users following the givern user
	 */
	public Set<UserGetDto> getFollowing(String username) {
		return userMapper
				.toDto(pullUser(username)
				.getFollowing()
				.stream()
				.filter(following -> following.getActive() == true)
				.collect(Collectors.toCollection(TreeSet::new)));
	}

	/**
	 * @param username
	 * @return a set of all tweets made by the user or users they are following
	 */
	public Set<TweetGetDto> getFeed(String username) {
		UserEntity user = pullUser(username);
		TreeSet<TweetEntity> allTweets = new TreeSet<TweetEntity>();

		user.getTweets().forEach(tweet -> {
			if(tweet.getActive()) {
				allTweets.add(tweet);
			}
		});
		
		user.getFollowing().forEach(follow -> {
			follow.getTweets().forEach(tweet -> {
				if(tweet.getActive()) {
					allTweets.add(tweet);
				}
			});
		});
		
		return tweetMapper.toDto(allTweets).descendingSet();
	}

	/**
	 * @param username
	 * @return a set of all tweets made by the given user
	 */
	public Set<TweetGetDto> getTweets(String username) {
		UserEntity user = pullUser(username);
		TreeSet<TweetEntity> allTweets = new TreeSet<TweetEntity>();

		user.getTweets().forEach(tweet -> {
			if(tweet.getActive()) {
				allTweets.add(tweet);
			}
		});
		
		return tweetMapper.toDto(allTweets).descendingSet();
	}

	/**
	 * @param username
	 * @return all tweets in which the given user was mentioned
	 */
	public Set<TweetGetDto> getMentions(String username) {
		return tweetMapper.toDto(new TreeSet<>(pullUser(username).getMentionedInTweets())).descendingSet();
	}
}
