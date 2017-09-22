/**
 * 
 */
package com.cooksys.twitterclone.controller;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitterclone.dto.HashtagGetDto;
import com.cooksys.twitterclone.dto.TweetGetDto;
import com.cooksys.twitterclone.service.HashtagService;

/**
 * @author Greg Hill
 *
 */
@RestController
@RequestMapping("/tags/")
public class HashtagController {

	private HashtagService hashtagService;
	
	private final Set<TweetGetDto> ERROR_SET = null;

	/**
	 * Constructor injecting service
	 * @param hashtagService
	 */
	public HashtagController(HashtagService hashtagService) {
		this.hashtagService = hashtagService;
	}
	
	/**
	 * @return all hashtags that are tracked by the server
	 */
	@GetMapping
	public Set<HashtagGetDto> getTags() {
		return hashtagService.getTags();
	}

	/**
	 * @param label
	 * @param response
	 * @return all tweets containing the given label
	 */
	@GetMapping("/{label}/")
	public Set<TweetGetDto> getTweetsByTag(@PathVariable String label, HttpServletResponse response) {
		Set<TweetGetDto> matchingTweets = hashtagService.getTweetsByTag(label);
			
		if(matchingTweets == ERROR_SET) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} 
		
		return matchingTweets;
	}
	
}
