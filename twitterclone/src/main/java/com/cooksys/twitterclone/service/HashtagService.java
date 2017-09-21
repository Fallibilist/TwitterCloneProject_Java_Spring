/**
 * 
 */
package com.cooksys.twitterclone.service;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.cooksys.twitterclone.dto.HashtagGetDto;
import com.cooksys.twitterclone.dto.TweetGetDto;
import com.cooksys.twitterclone.entity.TweetEntity;
import com.cooksys.twitterclone.mapper.HashtagMapper;
import com.cooksys.twitterclone.mapper.TweetMapper;
import com.cooksys.twitterclone.repository.HashtagJpaRepository;
import com.cooksys.twitterclone.repository.TweetJpaRepository;

/**
 * @author Greg Hill
 *
 */
@Service
public class HashtagService {

	private HashtagJpaRepository hashtagJpaRepository;
	private TweetJpaRepository tweetJpaRepository;
	private HashtagMapper hashtagMapper;
	private TweetMapper tweetMapper;

	public HashtagService(HashtagJpaRepository hashtagJpaRepository, TweetJpaRepository tweetJpaRepository, HashtagMapper hashtagMapper, TweetMapper tweetMapper) {
		this.hashtagJpaRepository = hashtagJpaRepository;
		this.tweetJpaRepository = tweetJpaRepository;
		this.hashtagMapper = hashtagMapper;
		this.tweetMapper = tweetMapper;
	}

	public Set<HashtagGetDto> getTags() {
		return hashtagMapper.toDto(new TreeSet<>(hashtagJpaRepository.findAll()));
	}

	public Set<TweetGetDto> getTweetsByTag(String label) {
		TreeSet<TweetEntity> allTweets = new TreeSet<TweetEntity>();
		StringBuffer hashtagLabel = new StringBuffer("#");
		
		hashtagLabel.append(label);

		// Gets all tweets with the tag
		tweetJpaRepository.findByContentContaining(hashtagLabel.toString()).forEach(tweet -> {
			if(tweet.getActive()) {
				allTweets.add(tweet);
			}
		});
		
		// Turns the tweets into dtos and reverses their order
		return tweetMapper.toDto(allTweets).descendingSet();
	}
}
