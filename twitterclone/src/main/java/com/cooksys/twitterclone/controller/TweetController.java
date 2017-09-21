/**
 * 
 */
package com.cooksys.twitterclone.controller;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitterclone.dto.ContextDto;
import com.cooksys.twitterclone.dto.CredentialsDto;
import com.cooksys.twitterclone.dto.HashtagGetDto;
import com.cooksys.twitterclone.dto.TweetGetDto;
import com.cooksys.twitterclone.dto.TweetSaveDto;
import com.cooksys.twitterclone.dto.UserGetDto;
import com.cooksys.twitterclone.service.TweetService;
import com.cooksys.twitterclone.service.ValidateService;

/**
 * @author Greg Hill
 *
 */
@RestController
@RequestMapping("/tweets/")
public class TweetController {
	
	private TweetService tweetService;
	private ValidateService validateService;
	
	private final TweetGetDto ERROR = null;

	public TweetController(TweetService tweetService, ValidateService validateService) {
		this.tweetService = tweetService;
		this.validateService = validateService;
	}
	
	@GetMapping
	public Set<TweetGetDto> getTweets() {
		return tweetService.getTweets();
	}
	
	@PostMapping
	public TweetGetDto postTweet(@RequestBody TweetSaveDto tweetSaveDto, HttpServletResponse response) {
		TweetGetDto postedTweet = tweetService.postTweet(tweetSaveDto);
		
		if(postedTweet == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return ERROR;
		} 
		
		return postedTweet;
	}

	@GetMapping("/{id}/")
	public TweetGetDto getTweet(@PathVariable Integer id, HttpServletResponse response) {
		TweetGetDto tweet = tweetService.getTweet(id);
			
		if(tweet == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return ERROR;
		} 
		
		return tweet;
	}
	
	@DeleteMapping("/{id}/")
	public TweetGetDto deleteTweet(@PathVariable Integer id, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response) {
		TweetGetDto deletedTweet = tweetService.deleteTweet(id, credentialsDto);
		
		if(deletedTweet == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return ERROR;
		} 
		
		return deletedTweet;
	}
	
	@PostMapping("/{id}/like/")
	public TweetGetDto likeTweet(@PathVariable Integer id, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response) {
		TweetGetDto likedTweet = tweetService.likeTweet(id, credentialsDto);
		
		if(likedTweet == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return ERROR;
		} else {
			return null;
		}
	}
	
	@PostMapping("/{id}/reply/")
	public TweetGetDto replyToTweet(@PathVariable Integer id, @RequestBody TweetSaveDto tweetSaveDto, HttpServletResponse response) {
		TweetGetDto reply = tweetService.replyToTweet(id, tweetSaveDto);
		
		if(reply == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return ERROR;
		} 
		
		return reply;
	}
	
	@PostMapping("/{id}/repost/")
	public TweetGetDto repostOfTweet(@PathVariable Integer id, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response) {
		TweetGetDto repost = tweetService.repostOfTweet(id, credentialsDto);
		
		if(repost == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return ERROR;
		} 
		
		return repost;
	}

	@GetMapping("/{id}/tags")
	public Set<HashtagGetDto> getTweetTags(@PathVariable Integer id, HttpServletResponse response) {
		if(!validateService.getTweetExists(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return tweetService.getTweetTags(id);
	}

	@GetMapping("/{id}/likes")
	public Set<UserGetDto> getTweetLikes(@PathVariable Integer id, HttpServletResponse response) {
		if(!validateService.getTweetExists(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return tweetService.getTweetLikes(id);
	}

	@GetMapping("/{id}/context")
	public ContextDto getContext(@PathVariable Integer id, HttpServletResponse response) {
		if(!validateService.getTweetExists(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return tweetService.getContext(id);
	}

	@GetMapping("/{id}/replies")
	public Set<TweetGetDto> getReplies(@PathVariable Integer id, HttpServletResponse response) {
		if(!validateService.getTweetExists(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return tweetService.getReplies(id);
	}

	@GetMapping("/{id}/reposts")
	public Set<TweetGetDto> getReposts(@PathVariable Integer id, HttpServletResponse response) {
		if(!validateService.getTweetExists(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return tweetService.getReposts(id);
	}

	@GetMapping("/{id}/mentions")
	public Set<UserGetDto> getMentions(@PathVariable Integer id, HttpServletResponse response) {
		if(!validateService.getTweetExists(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return tweetService.getMentions(id);
	}
	
}
