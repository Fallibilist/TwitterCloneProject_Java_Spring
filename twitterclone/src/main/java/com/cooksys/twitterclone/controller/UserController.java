/**
 * 
 */
package com.cooksys.twitterclone.controller;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitterclone.dto.CredentialsDto;
import com.cooksys.twitterclone.dto.TweetGetDto;
import com.cooksys.twitterclone.dto.UserGetDto;
import com.cooksys.twitterclone.dto.UserSaveDto;
import com.cooksys.twitterclone.service.UserService;

/**
 * @author Greg Hill
 *
 */
@RestController
@RequestMapping("/users/")
public class UserController {
	
	private UserService userService;
	
	private final UserGetDto SUCCESS = null;
	
	private final UserGetDto ERROR = null;
	
	private final Set<TweetGetDto> ERROR_TSET = null;
	
	private final Set<UserGetDto> ERROR_USET = null;

	/**
	 * Constructor injecting services
	 * @param userService
	 */
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * @return all users tracked by the server
	 */
	@GetMapping
	public Set<UserGetDto> getUsers() {
		return userService.getUsers();
	}
	
	/**
	 * @param userSaveDto
	 * @param response
	 * @return a copy of the user created by the request
	 */
	@PostMapping
	public UserGetDto postUser(@RequestBody UserSaveDto userSaveDto, HttpServletResponse response) {
		UserGetDto createdUser = userService.postUser(userSaveDto);
		
		if(createdUser == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return ERROR;
		} 
		
		return createdUser;
	}

	/**
	 * @param username
	 * @param response
	 * @return the user matching the username
	 */
	@GetMapping("/@{username}/")
	public UserGetDto getUser(@PathVariable String username, HttpServletResponse response) {
		UserGetDto user = userService.getUser(username);
			
		if(user == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return ERROR;
		} 
		
		return user;
	}
	
	/**
	 * @param username
	 * @param userSaveDto
	 * @param response
	 * @return the modified version of the user
	 */
	@PatchMapping("/@{username}/")
	public UserGetDto patchUser(@PathVariable String username, @RequestBody UserSaveDto userSaveDto, HttpServletResponse response) {
		UserGetDto patchedUser = userService.patchUser(username, userSaveDto);
		
		if(patchedUser == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return ERROR;
		} 
		
		return patchedUser;
	}
	
	/**
	 * @param username
	 * @param userSaveDto
	 * @param response
	 * @return the new user whose data has been overwritten
	 */
	@PutMapping("/@{username}/")
	public UserGetDto putUser(@PathVariable String username, @RequestBody UserSaveDto userSaveDto, HttpServletResponse response) {
		UserGetDto placedUser = userService.putUser(username, userSaveDto);
		
		if(placedUser == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return ERROR;
		} 
		
		return placedUser;
	}
	
	/**
	 * @param username
	 * @param credentialsDto
	 * @param response
	 * @return the user that was deleted
	 */
	@DeleteMapping("/@{username}/")
	public UserGetDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response) {
		UserGetDto deletedUser = userService.deleteUser(username, credentialsDto);
		
		if(deletedUser == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return ERROR;
		} 
		
		return deletedUser;
	}
	
	/**
	 * @param username
	 * @param credentialsDto
	 * @param response
	 * @return null one both success and failure, as specified
	 */
	@PostMapping("/@{username}/follow/")
	public UserGetDto followUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response) {
		UserGetDto followedUser = userService.followUser(username, credentialsDto);
		
		if(followedUser == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return ERROR;
		} else {
			return SUCCESS;
		}
	}
	
	/**
	 * @param username
	 * @param credentialsDto
	 * @param response
	 * @return null one both success and failure, as specified
	 */
	@PostMapping("/@{username}/unfollow/")
	public UserGetDto unfollowUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response) {
		UserGetDto unfollowedUser = userService.unfollowUser(username, credentialsDto);
		
		if(unfollowedUser == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return ERROR;
		} else {
			return SUCCESS;
		}
	}
	
	/**
	 * @param username
	 * @param response
	 * @return all users who are following the given user
	 */
	@GetMapping("/@{username}/followers/")
	public Set<UserGetDto> getFollowers(@PathVariable String username, HttpServletResponse response) {
		if(userService.getUser(username) == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return ERROR_USET;
		}
		
		Set<UserGetDto> followers = userService.getFollowers(username);
		
		return followers;
	}
	
	/**
	 * @param username
	 * @param response
	 * @return all users that the given user is following
	 */
	@GetMapping("/@{username}/following/")
	public Set<UserGetDto> getFollowing(@PathVariable String username, HttpServletResponse response) {
		if(userService.getUser(username) == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return ERROR_USET;
		}
		
		Set<UserGetDto> following = userService.getFollowing(username);
		
		return following;
	}
	
	/**
	 * @param username
	 * @param response
	 * @return all tweets posted by the user or those their follow
	 */
	@GetMapping("/@{username}/feed/")
	public Set<TweetGetDto> getFeed(@PathVariable String username, HttpServletResponse response) {
		if(userService.getUser(username) == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return ERROR_TSET;
		}
		
		return userService.getFeed(username);
	}
	
	/**
	 * @param username
	 * @param response
	 * @return all of the given user's tweets
	 */
	@GetMapping("/@{username}/tweets/")
	public Set<TweetGetDto> getTweets(@PathVariable String username, HttpServletResponse response) {
		if(userService.getUser(username) == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return ERROR_TSET;
		}
		
		return userService.getTweets(username);
	}
	
	/**
	 * @param username
	 * @param response
	 * @return all tweets in which the given user is mentioned
	 */
	@GetMapping("/@{username}/mentions/")
	public Set<TweetGetDto> getMentions(@PathVariable String username, HttpServletResponse response) {
		if(userService.getUser(username) == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return ERROR_TSET;
		}
		
		return userService.getMentions(username);
	}
	
}
