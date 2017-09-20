/**
 * 
 */
package com.cooksys.twitterclone.service;

import org.springframework.stereotype.Service;

import com.cooksys.twitterclone.entity.UserEntity;
import com.cooksys.twitterclone.repository.HashtagJpaRepository;
import com.cooksys.twitterclone.repository.UserJpaRepository;

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
		if(hashtagJpaRepository.findByLabel(label) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getUsernameAvailable(String username) {
		UserEntity user = userJpaRepository.findByCredentialsUsername(username);
		if(user == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getUsernameExists(String username) {
		UserEntity user = userJpaRepository.findByCredentialsUsername(username);
		if(user != null && user.getActive()) {
			return true;
		} else {
			return false;
		}
	}
	
}
