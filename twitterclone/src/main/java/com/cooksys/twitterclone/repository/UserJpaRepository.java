/**
 * 
 */
package com.cooksys.twitterclone.repository;

import java.util.TreeSet;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twitterclone.entity.UserEntity;

/**
 * @author Greg Hill
 *
 */
public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {

	UserEntity findByCredentialsUsername(String username);
	
	TreeSet<UserEntity> findByActive(Boolean active);
	
}
