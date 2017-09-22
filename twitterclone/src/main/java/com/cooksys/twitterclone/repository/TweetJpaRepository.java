/**
 * 
 */
package com.cooksys.twitterclone.repository;

import java.util.TreeSet;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twitterclone.entity.TweetEntity;

/**
 * @author Greg Hill
 *
 */
public interface TweetJpaRepository extends JpaRepository<TweetEntity, Integer> {

	TreeSet<TweetEntity> findByActive(Boolean active);

	TreeSet<TweetEntity> findByInReplyToIs(TweetEntity id);
	
	TreeSet<TweetEntity> findByRepostOfIs(TweetEntity id);
	
}
