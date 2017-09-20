/**
 * 
 */
package com.cooksys.twitterclone.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitterclone.service.TweetService;

/**
 * @author Greg Hill
 *
 */
@RestController
public class TweetController {
	
	private TweetService tweetService;

	public TweetController(TweetService tweetService) {
		this.tweetService = tweetService;
	}
}
