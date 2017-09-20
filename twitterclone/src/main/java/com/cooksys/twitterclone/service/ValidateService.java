/**
 * 
 */
package com.cooksys.twitterclone.service;

import org.springframework.stereotype.Service;

import com.cooksys.twitterclone.entity.UserEntity;
import com.cooksys.twitterclone.entity.embeddable.CredentialsEmbeddable;
import com.cooksys.twitterclone.repository.HashtagJpaRepository;
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

	public ValidateService(HashtagJpaRepository hashtagJpaRepository, UserJpaRepository userJpaRepository) {
		this.hashtagJpaRepository = hashtagJpaRepository;
		this.userJpaRepository = userJpaRepository;
	}
	
	public boolean getTagExists(String label) {
		return hashtagJpaRepository.findByLabel(label) != null;
	}
	
	/**
	 * @param username
	 * @return true if there was never a user with this username, false otherwise
	 */
	public boolean getUsernameAvailable(String username) {
		UserEntity user = userJpaRepository.findByCredentialsUsername(username);
		return user == null;
	}

	/**
	 * @param username
	 * @return true if the user is in the database and is currently active, false otherwise
	 */
	public boolean getUsernameExists(String username) {
		UserEntity user = userJpaRepository.findByCredentialsUsername(username);
		return (user != null && user.getActive());
	}

	/**
	 * @param username
	 * @return true if the user is in the database and is currently inactive, false otherwise
	 */
	public boolean getUsernameInactive(String username) {
		UserEntity user = userJpaRepository.findByCredentialsUsername(username);
		return (user != null && !user.getActive());
	}

	/**
	 * @param credentials
	 * @return true if the credentials match a user in the database, false otherwise
	 */
	public boolean validateCredentials(CredentialsEmbeddable credentials) {
		UserEntity user = userJpaRepository.findByCredentialsUsername(credentials.getUsername());
		return user.getCredentials().getPassword().equals(credentials.getPassword());
	}

	/**
	 * @param user
	 * @return true if the user has valid information in all required fields, false otherwise
	 */
	public boolean validUserFields(UserEntity user) {
		if(user.getCredentials() == null ||
			user.getCredentials().getUsername() == null ||
			user.getCredentials().getPassword() == null ||
			user.getProfile().getEmail() == null) {
			return false;
		} else {
			return true;
		}
	}
	
}
