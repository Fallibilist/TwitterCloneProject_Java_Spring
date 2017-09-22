/**
 * 
 */
package com.cooksys.twitterclone.service;

import org.springframework.stereotype.Service;

import com.cooksys.twitterclone.dto.TweetGetDto;
import com.cooksys.twitterclone.entity.HashtagEntity;
import com.cooksys.twitterclone.entity.TweetEntity;
import com.cooksys.twitterclone.entity.UserEntity;
import com.cooksys.twitterclone.entity.embeddable.CredentialsEmbeddable;
import com.cooksys.twitterclone.repository.HashtagJpaRepository;
import com.cooksys.twitterclone.repository.TweetJpaRepository;
import com.cooksys.twitterclone.repository.UserJpaRepository;

/**
 * @author Greg Hill
 *
 */
/**
 * @author Greg Hill
 *
 */
@Service
public class ValidateService {
	
	private HashtagJpaRepository hashtagJpaRepository;
	
	private UserJpaRepository userJpaRepository;
	
	private TweetJpaRepository tweetJpaRepository;
	
	private final TweetEntity ERROR_TWEET = null;
	
	private final HashtagEntity ERROR_TAG = null;
	
	private final UserEntity ERROR_USER = null;

	/**
	 * Constructor injecting repositories
	 * @param hashtagJpaRepository
	 * @param userJpaRepository
	 * @param tweetJpaRepository
	 */
	public ValidateService(HashtagJpaRepository hashtagJpaRepository, UserJpaRepository userJpaRepository, TweetJpaRepository tweetJpaRepository) {
		this.hashtagJpaRepository = hashtagJpaRepository;
		this.userJpaRepository = userJpaRepository;
		this.tweetJpaRepository = tweetJpaRepository;
	}
	
	/**
	 * @param username
	 * @return a user by username
	 */
	public UserEntity pullUser(String username) {
		return userJpaRepository.findByCredentialsUsername(username);
	}
	
	/**
	 * @param label
	 * @return a hashtag by label
	 */
	public HashtagEntity pullTag(String label) {
		return hashtagJpaRepository.findByLabel(label);
	}
	
	/**
	 * @param label
	 * @return whether or not the given label is associated with a hashtag in the database
	 */
	public Boolean getTagExists(String label) {
		return pullTag(label) != ERROR_TAG;
	}

	/**
	 * @param id
	 * @return a tweet by id
	 */
	public TweetEntity pullTweet(Integer id) {
		return tweetJpaRepository.findOne(id);
	}
	
	/**
	 * @param id
	 * @return whether or not the given tweet exists in the database and is active
	 */
	public Boolean getTweetExists(Integer id) {
		return pullTweet(id) != ERROR_TWEET && pullTweet(id).getActive();
	}
	
	/**
	 * @param username
	 * @return true if there was never a user with this username, false otherwise
	 */
	public Boolean getUsernameAvailable(String username) {
		UserEntity user = pullUser(username);
		return user == ERROR_USER;
	}

	/**
	 * @param username
	 * @return true if the user is in the database and is currently active, false otherwise
	 */
	public Boolean getUsernameExists(String username) {
		UserEntity user = pullUser(username);
		return (user != ERROR_USER && user.getActive());
	}

	/**
	 * @param username
	 * @return true if the user is in the database and is currently inactive, false otherwise
	 */
	public Boolean getUsernameInactive(String username) {
		UserEntity user = pullUser(username);
		return (user != ERROR_USER && !user.getActive());
	}

	/**
	 * @param credentials
	 * @return true if the credentials match a user in the database, false otherwise
	 */
	public Boolean validateCredentials(CredentialsEmbeddable credentials) {
		UserEntity user = pullUser(credentials.getUsername());
		if(user != ERROR_USER) {
			return user.getCredentials().getPassword().equals(credentials.getPassword());
		} else {
			return false;
		}
	}

	/**
	 * @param user
	 * @return true if the user has valid information in all required fields, false otherwise
	 */
	public Boolean validUserFields(UserEntity user) {
		if(user.getCredentials() == null ||
			user.getCredentials().getUsername() == null ||
			user.getCredentials().getPassword() == null ||
			user.getProfile().getEmail() == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @param tweet
	 * @return validates the fields of the new tweet
	 */
	public Boolean validTweetFields(TweetEntity tweet) {
		if(tweet.getContent() == null) {
			return false;
		} else {
			return true;
		}
	}
	
}
