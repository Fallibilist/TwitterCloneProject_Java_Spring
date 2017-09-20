/**
 * 
 */
package com.cooksys.twitterclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twitterclone.entity.HashtagEntity;

/**
 * @author Greg Hill
 *
 */
public interface HashtagJpaRepository extends JpaRepository<HashtagEntity, Integer> {

	HashtagEntity findByLabel(String label);
	
}
