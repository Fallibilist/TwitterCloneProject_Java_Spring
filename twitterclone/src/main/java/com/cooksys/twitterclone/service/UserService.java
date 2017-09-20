/**
 * 
 */
package com.cooksys.twitterclone.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.cooksys.twitterclone.dto.UserGetDto;
import com.cooksys.twitterclone.dto.UserSaveDto;
import com.cooksys.twitterclone.entity.UserEntity;
import com.cooksys.twitterclone.entity.embeddable.CredentialsEmbeddable;
import com.cooksys.twitterclone.mapper.CredentialsMapper;
import com.cooksys.twitterclone.mapper.ProfileMapper;
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
	private ValidateService validateService;
	private final UserGetDto ERROR = null;

	public UserService(UserJpaRepository userJpaRepository, UserMapper userMapper, CredentialsMapper credentialsMapper, 
			ProfileMapper profileMapper, ValidateService validateService) {
		this.userJpaRepository = userJpaRepository;
		this.userMapper = userMapper;
		this.credentialsMapper = credentialsMapper;
		this.profileMapper = profileMapper;
		this.validateService = validateService;
	}

	public Set<UserGetDto> getUsers() {
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
				user = userJpaRepository.findByCredentialsUsername(credentials.getUsername());
				user.setActive(true);
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
			return userMapper.toDtoGet(userJpaRepository.findByCredentialsUsername(username));
		} else {
			return ERROR;
		}
	}

	public UserGetDto patchUser(String username, UserSaveDto userSaveDto) {
		UserEntity user = userMapper.fromDtoSave(userSaveDto, profileMapper, credentialsMapper);
		CredentialsEmbeddable credentials = user.getCredentials();
		
		return null;
	}
}
