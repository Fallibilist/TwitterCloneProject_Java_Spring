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
import com.cooksys.twitterclone.dto.TweetRepostDto;
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
	
	private final TweetGetDto SUCCESS = null;
	
	private final TweetRepostDto ERROR_REPOST = null;

	private final ContextDto ERROR_CONTEXT = null;
	
	private final Set<TweetGetDto> ERROR_TSET = null;
	
	private final Set<UserGetDto> ERROR_USET = null;
	
	private final Set<HashtagGetDto> ERROR_HSET = null;

	/**
	 * Constructor injecting services
	 * @param tweetService
	 * @param validateService
	 */
	public TweetController(TweetService tweetService, ValidateService validateService) {
		this.tweetService = tweetService;
		this.validateService = validateService;
	}
	
	/**
	 * @return all tweets tracked by the server
	 */
	@GetMapping
	public Set<TweetGetDto> getTweets() {
		return tweetService.getTweets();
	}
	
	/**
	 * @param tweetSaveDto
	 * @param response
	 * @return tweet that was created
	 */
	@PostMapping
	public TweetGetDto postTweet(@RequestBody TweetSaveDto tweetSaveDto, HttpServletResponse response) {
		TweetGetDto postedTweet = tweetService.postTweet(tweetSaveDto);
		
		if(postedTweet == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return ERROR;
		} 
		
		return postedTweet;
	}

	/**
	 * @param id
	 * @param response
	 * @return tweet whose id matches the request
	 */
	@GetMapping("/{id}/")
	public TweetGetDto getTweet(@PathVariable Integer id, HttpServletResponse response) {
		TweetGetDto tweet = tweetService.getTweet(id);
			
		if(tweet == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return ERROR;
		} 
		
		return tweet;
	}
	
	/**
	 * @param id
	 * @param credentialsDto
	 * @param response
	 * @return a representation of the deleted tweet
	 */
	@DeleteMapping("/{id}/")
	public TweetGetDto deleteTweet(@PathVariable Integer id, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response) {
		TweetGetDto deletedTweet = tweetService.deleteTweet(id, credentialsDto);
		
		if(deletedTweet == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return ERROR;
		} 
		
		return deletedTweet;
	}
	
	/**
	 * @param id
	 * @param credentialsDto
	 * @param response
	 * @return the tweet that was liked by user
	 */
	@PostMapping("/{id}/like/")
	public TweetGetDto likeTweet(@PathVariable Integer id, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response) {
		TweetGetDto likedTweet = tweetService.likeTweet(id, credentialsDto);
		
		if(likedTweet == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return ERROR;
		} else {
			return SUCCESS;
		}
	}
	
	/**
	 * @param id
	 * @param tweetSaveDto
	 * @param response
	 * @return the reply created from the request
	 */
	@PostMapping("/{id}/reply/")
	public TweetGetDto replyToTweet(@PathVariable Integer id, @RequestBody TweetSaveDto tweetSaveDto, HttpServletResponse response) {
		TweetGetDto reply = tweetService.replyToTweet(id, tweetSaveDto);
		
		if(reply == ERROR) {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return ERROR;
		} 
		
		return reply;
	}
	
	/**
	 * @param id
	 * @param credentialsDto
	 * @param response
	 * @return the repost information as well as a copy of the original post
	 */
	@PostMapping("/{id}/repost/")
	public TweetRepostDto repostOfTweet(@PathVariable Integer id, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response) {
		TweetRepostDto repost = tweetService.repostOfTweet(id, credentialsDto);
		
		if(repost == ERROR_REPOST) {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return ERROR_REPOST;
		} 
		
		return repost;
	}

	/**
	 * @param id
	 * @param response
	 * @return all of the tags contained in the given tweet
	 */
	@GetMapping("/{id}/tags")
	public Set<HashtagGetDto> getTweetTags(@PathVariable Integer id, HttpServletResponse response) {
		if(!validateService.getTweetExists(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return ERROR_HSET;
		}
		
		return tweetService.getTweetTags(id);
	}

	/**
	 * @param id
	 * @param response
	 * @return all of the users that liked the given tweet
	 */
	@GetMapping("/{id}/likes")
	public Set<UserGetDto> getTweetLikes(@PathVariable Integer id, HttpServletResponse response) {
		if(!validateService.getTweetExists(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return ERROR_USET;
		}
		
		return tweetService.getTweetLikes(id);
	}

	/**
	 * @param id
	 * @param response
	 * @return all of the users in the current tweet chain and the current tweet
	 */
	@GetMapping("/{id}/context")
	public ContextDto getContext(@PathVariable Integer id, HttpServletResponse response) {
		if(!validateService.getTweetExists(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return ERROR_CONTEXT ;
		}
		
		return tweetService.getContext(id);
	}

	/**
	 * @param id
	 * @param response
	 * @return all tweets that replied to the current tweet
	 */
	@GetMapping("/{id}/replies")
	public Set<TweetGetDto> getReplies(@PathVariable Integer id, HttpServletResponse response) {
		if(!validateService.getTweetExists(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return ERROR_TSET;
		}
		
		return tweetService.getReplies(id);
	}

	/**
	 * @param id
	 * @param response
	 * @return all tweets that are reposts of the current tweet
	 */
	@GetMapping("/{id}/reposts")
	public Set<TweetGetDto> getReposts(@PathVariable Integer id, HttpServletResponse response) {
		if(!validateService.getTweetExists(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return ERROR_TSET;
		}
		
		return tweetService.getReposts(id);
	}

	/**
	 * @param id
	 * @param response
	 * @return all users that are mentioned in the given tweet
	 */
	@GetMapping("/{id}/mentions")
	public Set<UserGetDto> getMentions(@PathVariable Integer id, HttpServletResponse response) {
		if(!validateService.getTweetExists(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return ERROR_USET;
		}
		
		return tweetService.getMentions(id);
	}
	
}
