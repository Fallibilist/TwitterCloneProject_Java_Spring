/**
 * 
 */
package com.cooksys.twitterclone.controller;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		
		if(createdUser == null) {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return ERROR;
		} 
		
		return createdUser;
	}

	@GetMapping("/@{username}/")
	public UserGetDto getUser(@PathVariable String username, HttpServletResponse response) {
		UserGetDto user = userService.getUser(username);
			
		if(user == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return ERROR;
		} 
		
		return user;
	}
	
	@PatchMapping("/@{username}/")
	public UserGetDto patchUser(@PathVariable String username, @RequestBody UserSaveDto userSaveDto, HttpServletResponse response) {
		UserGetDto patchedUser = userService.patchUser(username, userSaveDto);
		
		if(patchedUser == null) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return ERROR;
		} 
		
		return patchedUser;
	}
	
}
