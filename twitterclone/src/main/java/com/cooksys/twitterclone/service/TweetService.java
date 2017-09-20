/**
 * 
 */
package com.cooksys.twitterclone.service;

import org.springframework.stereotype.Service;

import com.cooksys.twitterclone.mapper.TweetMapper;
import com.cooksys.twitterclone.repository.TweetJpaRespository;

/**
 * @author Greg Hill
 *
 */
@Service
public class TweetService {
	
	private TweetJpaRespository tweetJpaRespository;
	private TweetMapper tweetMapper;

	public TweetService(TweetJpaRespository tweetJpaRespository, TweetMapper tweetMapper) {
		this.tweetJpaRespository = tweetJpaRespository;
		this.tweetMapper = tweetMapper;
	}
}
