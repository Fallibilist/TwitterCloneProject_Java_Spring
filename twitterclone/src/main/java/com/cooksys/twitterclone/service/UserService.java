/**
 * 
 */
package com.cooksys.twitterclone.service;

import java.util.List;
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
import com.cooksys.twitterclone.repository.TweetJpaRepository;
import com.cooksys.twitterclone.repository.UserJpaRepository;
import com.cooksys.twitterclone.utilities.Utilities;

/**
 * @author Greg Hill
 *
 */
@Service
public class UserService {

	private UserJpaRepository userJpaRepository;
	private TweetJpaRepository tweetJpaRepository;
	private UserMapper userMapper;
	private CredentialsMapper credentialsMapper;
	private ProfileMapper profileMapper;
	private TweetMapper tweetMapper;
	private ValidateService validateService;
	
	private final UserGetDto ERROR = null;

	public UserService(UserJpaRepository userJpaRepository, TweetJpaRepository tweetJpaRepository, UserMapper userMapper, CredentialsMapper credentialsMapper, 
			ProfileMapper profileMapper, TweetMapper tweetMapper, ValidateService validateService) {
		this.userJpaRepository = userJpaRepository;
		this.tweetJpaRepository = tweetJpaRepository;
		this.userMapper = userMapper;
		this.credentialsMapper = credentialsMapper;
		this.profileMapper = profileMapper;
		this.tweetMapper = tweetMapper;
		this.validateService = validateService;
	}
	
	public UserEntity pullUser(String username) {
		return validateService.pullUser(username);
	}

	public TreeSet<UserGetDto> getUsers() {
		return userMapper.toDto(userJpaRepository.findByActive(true));
	}

	public UserGetDto postUser(UserSaveDto userSaveDto) {
		UserEntity user = userMapper.fromDtoSave(userSaveDto, profileMapper, credentialsMapper);
		CredentialsEmbeddable credentials = user.getCredentials();
		
		// Ensures that the fields for the new user are valid
		if(!validateService.validUserFields(user)) {
			return ERROR;
		}
		
		if(validateService.getUsernameAvailable(credentials.getUsername())) {
			// Username available, create new user
			user.setJoined(Utilities.currentTime());
			userJpaRepository.save(user);
			return userMapper.toDtoGet(user);
		} else if(validateService.getUsernameInactive(credentials.getUsername())) {
			// User is inactive, reactivate user if credentials are correct
			if(validateService.validateCredentials(credentials)) {
				user = pullUser(credentials.getUsername());
				user.setActive(true);
				
				// Reactivates any non-deleted tweets
				Set<TweetEntity> deletedTweets = user.getDeletedTweets();

				// For debugging
				deletedTweets.forEach(tweet -> System.out.println(tweet.getId()));
				System.out.println("GRE\n\n\n\ngdfgfd\n\n\nsgsg\n\n\n");
				user.getDeletedTweets().forEach(tweet -> System.out.println(tweet.getId()));
				
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
			// User already exists and is active, tell controller to throw error
			return ERROR;
		}
	}

	public UserGetDto getUser(String username) {
		if(validateService.getUsernameExists(username)) {
			return userMapper.toDtoGet(pullUser(username));
		} else {
			return ERROR;
		}
	}

	public UserGetDto patchUser(String username, UserSaveDto userSaveDto) {
		UserEntity user = userMapper.fromDtoSave(userSaveDto, profileMapper, credentialsMapper);
		CredentialsEmbeddable newCredentials = user.getCredentials();
		ProfileEmbeddable newProfile = user.getProfile();

		// Checks if the user exists and if the credentials are correct
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

	public UserGetDto putUser(String username, UserSaveDto userSaveDto) {
		UserEntity user = userMapper.fromDtoSave(userSaveDto, profileMapper, credentialsMapper);
		CredentialsEmbeddable newCredentials = user.getCredentials();
		ProfileEmbeddable newProfile = user.getProfile();

		// Checks if the user exists and if the credentials are correct
		if(!validateService.getUsernameExists(username) || 
			!validateService.validateCredentials(newCredentials) ||
			!username.equals(newCredentials.getUsername()) ||
			user.getProfile().getEmail().equals(null)) {
			return ERROR;
		}
		
		user = pullUser(username);
		user.setProfile(newProfile);
		
		userJpaRepository.save(user);
		return userMapper.toDtoGet(user);
	}

	public UserGetDto deleteUser(String username, CredentialsDto credentialsDto) {
		CredentialsEmbeddable credentials = credentialsMapper.fromDto(credentialsDto);

		// Checks if the user exists and if the credentials are correct
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

	public UserGetDto followUser(String username, CredentialsDto credentialsDto) {
		CredentialsEmbeddable credentials = credentialsMapper.fromDto(credentialsDto);

		// Checks if both users exist and if the credentials are correct
		if(!validateService.getUsernameExists(username) || 
			!validateService.getUsernameExists(credentials.getUsername()) ||
			!validateService.validateCredentials(credentials)) {
			return ERROR;
		}

		UserEntity userToFollow = pullUser(username);
		
		UserEntity user = pullUser(credentials.getUsername());
		Set<UserEntity> following = user.getFollowing();
		
		// Checks if the user is already following
		if(following.contains(userToFollow)) {
			return ERROR;
		}
		
		following.add(userToFollow);
		userJpaRepository.save(user);
		return userMapper.toDtoGet(user);
	}

	public UserGetDto unfollowUser(String username, CredentialsDto credentialsDto) {
		CredentialsEmbeddable credentials = credentialsMapper.fromDto(credentialsDto);

		// Checks if both users exist and if the credentials are correct
		if(!validateService.getUsernameExists(username) || 
			!validateService.getUsernameExists(credentials.getUsername()) ||
			!validateService.validateCredentials(credentials)) {
			return ERROR;
		}

		UserEntity userToUnfollow = pullUser(username);
		
		UserEntity user = pullUser(credentials.getUsername());
		Set<UserEntity> following = user.getFollowing();
		
		// Checks if the user is already following
		if(!following.contains(userToUnfollow)) {
			return ERROR;
		}
		
		following.remove(userToUnfollow);
		userJpaRepository.save(user);
		return userMapper.toDtoGet(user);
	}

	public TreeSet<UserGetDto> getFollowers(String username) {
		return userMapper
					.toDto(pullUser(username)
					.getFollowers()
					.stream()
					.filter(follower -> follower.getActive() == true)
					.collect(Collectors.toCollection(TreeSet::new)));
	}

	public Set<UserGetDto> getFollowing(String username) {
		return userMapper
				.toDto(pullUser(username)
				.getFollowing()
				.stream()
				.filter(following -> following.getActive() == true)
				.collect(Collectors.toCollection(TreeSet::new)));
	}

	public Set<TweetGetDto> getFeed(String username) {
		UserEntity user = pullUser(username);
		TreeSet<TweetEntity> allTweets = new TreeSet<TweetEntity>();

		// Gets all tweets of the current user and all tweets of every user they are following
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
		
		// Turns the tweets into dtos and reverses their order
		return tweetMapper.toDto(allTweets).descendingSet();
	}

	public Set<TweetGetDto> getTweets(String username) {
		UserEntity user = pullUser(username);
		TreeSet<TweetEntity> allTweets = new TreeSet<TweetEntity>();

		// Gets all tweets of the current user
		user.getTweets().forEach(tweet -> {
			if(tweet.getActive()) {
				allTweets.add(tweet);
			}
		});
		
		// Turns the tweets into dtos and reverses their order
		return tweetMapper.toDto(allTweets).descendingSet();
	}

	public Set<TweetGetDto> getMentions(String username) {
		// Turns the tweets into dtos and reverses their order
		return tweetMapper.toDto(new TreeSet<>(pullUser(username).getMentionedInTweets())).descendingSet();
	}
}
