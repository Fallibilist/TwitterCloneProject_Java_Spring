/**
 * 
 */
package com.cooksys.twitterclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twitterclone.entity.TweetEntity;

/**
 * @author Greg Hill
 *
 */
public interface TweetJpaRespository extends JpaRepository<TweetEntity, Integer> {

}
