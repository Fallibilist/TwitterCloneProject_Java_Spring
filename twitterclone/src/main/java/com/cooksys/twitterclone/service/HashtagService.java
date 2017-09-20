/**
 * 
 */
package com.cooksys.twitterclone.service;

import org.springframework.stereotype.Service;

import com.cooksys.twitterclone.mapper.HashtagMapper;
import com.cooksys.twitterclone.repository.HashtagJpaRepository;

/**
 * @author Greg Hill
 *
 */
@Service
public class HashtagService {

	private HashtagJpaRepository hashtagJpaRepository;
	private HashtagMapper hashtagMapper;

	public HashtagService(HashtagJpaRepository hashtagJpaRepository, HashtagMapper hashtagMapper) {
		this.hashtagJpaRepository = hashtagJpaRepository;
		this.hashtagMapper = hashtagMapper;
	}
}
