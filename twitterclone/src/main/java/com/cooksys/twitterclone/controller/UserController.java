/**
 * 
 */
package com.cooksys.twitterclone.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitterclone.service.UserService;

/**
 * @author Greg Hill
 *
 */
@RestController
public class UserController {
	
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	
	
}
