/**
 * 
 */
package com.cooksys.twitterclone.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitterclone.service.HashtagService;

/**
 * @author Greg Hill
 *
 */
@RestController
@RequestMapping("/tags/")
public class HashtagController {

	private HashtagService hashtagService;

	public HashtagController(HashtagService hashtagService) {
		this.hashtagService = hashtagService;
	}
}
