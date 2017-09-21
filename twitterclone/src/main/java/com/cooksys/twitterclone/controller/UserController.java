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
	
	private final UserGetDto ERROR = null;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public Set<UserGetDto> getUsers() {
		return userService.getUsers();
	}
	
	@PostMapping
	public UserGetDto postUser(@RequestBody UserSaveDto userSaveDto, HttpServletResponse response) {
		UserGetDto createdUser = userService.postUser(userSaveDto);
		
		if(createdUser == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return ERROR;
		} 
		
		return createdUser;
	}

	@GetMapping("/@{username}/")
	public UserGetDto getUser(@PathVariable String username, HttpServletResponse response) {
		UserGetDto user = userService.getUser(username);
			
		if(user == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return ERROR;
		} 
		
		return user;
	}
	
	@PatchMapping("/@{username}/")
	public UserGetDto patchUser(@PathVariable String username, @RequestBody UserSaveDto userSaveDto, HttpServletResponse response) {
		UserGetDto patchedUser = userService.patchUser(username, userSaveDto);
		
		if(patchedUser == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return ERROR;
		} 
		
		return patchedUser;
	}
	
	@PutMapping("/@{username}/")
	public UserGetDto putUser(@PathVariable String username, @RequestBody UserSaveDto userSaveDto, HttpServletResponse response) {
		UserGetDto placedUser = userService.putUser(username, userSaveDto);
		
		if(placedUser == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return ERROR;
		} 
		
		return placedUser;
	}
	
	@DeleteMapping("/@{username}/")
	public UserGetDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response) {
		UserGetDto deletedUser = userService.deleteUser(username, credentialsDto);
		
		if(deletedUser == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return ERROR;
		} 
		
		return deletedUser;
	}
	
	@PostMapping("/@{username}/follow/")
	public UserGetDto followUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response) {
		UserGetDto followedUser = userService.followUser(username, credentialsDto);
		
		if(followedUser == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return ERROR;
		} else {
			return null;
		}
	}
	
	@PostMapping("/@{username}/unfollow/")
	public UserGetDto unfollowUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response) {
		UserGetDto unfollowedUser = userService.unfollowUser(username, credentialsDto);
		
		if(unfollowedUser == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return ERROR;
		} else {
			return null;
		}
	}
	
	@GetMapping("/@{username}/followers/")
	public Set<UserGetDto> getFollowers(@PathVariable String username, HttpServletResponse response) {
		if(userService.getUser(username) == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		Set<UserGetDto> followers = userService.getFollowers(username);
		
		return followers;
	}
	
	@GetMapping("/@{username}/following/")
	public Set<UserGetDto> getFollowing(@PathVariable String username, HttpServletResponse response) {
		if(userService.getUser(username) == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		Set<UserGetDto> following = userService.getFollowing(username);
		
		return following;
	}
	
	@GetMapping("/@{username}/feed/")
	public Set<TweetGetDto> getFeed(@PathVariable String username, HttpServletResponse response) {
		if(userService.getUser(username) == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return userService.getFeed(username);
	}
	
	@GetMapping("/@{username}/tweets/")
	public Set<TweetGetDto> getTweets(@PathVariable String username, HttpServletResponse response) {
		if(userService.getUser(username) == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return userService.getTweets(username);
	}
	
	@GetMapping("/@{username}/mentions/")
	public Set<TweetGetDto> getMentions(@PathVariable String username, HttpServletResponse response) {
		if(userService.getUser(username) == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return userService.getMentions(username);
	}
	
}
