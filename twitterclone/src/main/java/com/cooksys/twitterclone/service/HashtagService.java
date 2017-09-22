/**
 * 
 */
package com.cooksys.twitterclone.service;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.cooksys.twitterclone.dto.HashtagGetDto;
import com.cooksys.twitterclone.dto.TweetGetDto;
import com.cooksys.twitterclone.mapper.HashtagMapper;
import com.cooksys.twitterclone.mapper.TweetMapper;
import com.cooksys.twitterclone.repository.HashtagJpaRepository;

/**
 * @author Greg Hill
 *
 */
@Service
public class HashtagService {

	private HashtagJpaRepository hashtagJpaRepository;
	
	private HashtagMapper hashtagMapper;
	
	private TweetMapper tweetMapper;
	
	private ValidateService validateService;

	/**
	 * Constructor injecting repositories, mappers, and services
	 * @param hashtagJpaRepository
	 * @param hashtagMapper
	 * @param tweetMapper
	 * @param validateService
	 */
	public HashtagService(HashtagJpaRepository hashtagJpaRepository, HashtagMapper hashtagMapper, 
			TweetMapper tweetMapper, ValidateService validateService) {
		this.hashtagJpaRepository = hashtagJpaRepository;
		this.hashtagMapper = hashtagMapper;
		this.tweetMapper = tweetMapper;
		this.validateService = validateService;
	}

	/**
	 * @return a sorted set of all tags
	 */
	public Set<HashtagGetDto> getTags() {
		return hashtagMapper.toDto(new TreeSet<>(hashtagJpaRepository.findAll()));
	}

	/**
	 * @param label
	 * @return a reverse chronological set of all tweets with the given tag
	 */
	public Set<TweetGetDto> getTweetsByTag(String label) {
		return tweetMapper.toDto(new TreeSet<>(validateService.pullTag(label).getTweets())).descendingSet();
	}
}
