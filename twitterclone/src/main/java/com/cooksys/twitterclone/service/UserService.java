/**
 * 
 */
package com.cooksys.twitterclone.service;

import org.springframework.stereotype.Service;

import com.cooksys.twitterclone.mapper.CredentialsMapper;
import com.cooksys.twitterclone.mapper.ProfileMapper;
import com.cooksys.twitterclone.mapper.UserMapper;
import com.cooksys.twitterclone.repository.UserJpaRepository;

/**
 * @author Greg Hill
 *
 */
@Service
public class UserService {

	private UserJpaRepository userJpaRepository;
	private UserMapper userMapper;

	public UserService(UserJpaRepository userJpaRepository, UserMapper userMapper, CredentialsMapper credentialsMapper, ProfileMapper profileMapper) {
		this.userJpaRepository = userJpaRepository;
		this.userMapper = userMapper;
	}
}
