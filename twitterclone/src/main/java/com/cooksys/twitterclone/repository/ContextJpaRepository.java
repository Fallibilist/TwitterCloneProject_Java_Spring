/**
 * 
 */
package com.cooksys.twitterclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twitterclone.entity.ContextEntity;

/**
 * @author Greg Hill
 *
 */
public interface ContextJpaRepository extends JpaRepository<ContextEntity, Integer> {

}
