/**
 * 
 */
package com.cooksys.twitterclone.service;

import com.cooksys.twitterclone.mapper.ContextMapper;
import com.cooksys.twitterclone.repository.ContextJpaRepository;

/**
 * @author Greg Hill
 *
 */
public class ContextService {
	
	private ContextJpaRepository contextJpaRepository;
	private ContextMapper contextMapper;

	public ContextService(ContextJpaRepository contextJpaRepository, ContextMapper contextMapper) {
		this.contextJpaRepository = contextJpaRepository;
		this.contextMapper = contextMapper;
	}
}
