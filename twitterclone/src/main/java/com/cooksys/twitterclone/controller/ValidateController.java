/**
 * 
 */
package com.cooksys.twitterclone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitterclone.service.ValidateService;

/**
 * @author Greg Hill
 *
 */
@RestController
@RequestMapping("/validate/")
public class ValidateController {
	
	private ValidateService validateService;

	public ValidateController(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	@GetMapping("/tag/exists/{label}/")
	public boolean getTagExists(@PathVariable String label) {
		return validateService.getTagExists(label);
	}
	
	@GetMapping("/username/available/@{username}/")
	public boolean getUsernameAvailable(@PathVariable String username) {
		return validateService.getUsernameAvailable(username);
	}
	
	@GetMapping("/username/exists/@{username}/")
	public boolean getUsernameExists(@PathVariable String username) {
		return validateService.getUsernameExists(username);
	}
	
}
