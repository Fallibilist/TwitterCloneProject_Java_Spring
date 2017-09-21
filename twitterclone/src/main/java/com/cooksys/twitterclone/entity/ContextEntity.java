/**
 * 
 */
package com.cooksys.twitterclone.entity;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author Greg Hill
 *
 */
@Entity
public class ContextEntity {

	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToMany(mappedBy = "context")
	private Set<TweetEntity> fullTweetContext;

	/**
	 * Default Constructor
	 */
	public ContextEntity() { }

	/**
	 * @param id
	 * @param before
	 * @param after
	 */
	public ContextEntity(Integer id, TreeSet<TweetEntity> fullTweetContext) {
		this();
		this.id = id;
		this.fullTweetContext = fullTweetContext;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the fullTweetContext
	 */
	public Set<TweetEntity> getFullTweetContext() {
		if(fullTweetContext.equals(null)) {
			fullTweetContext = new TreeSet<TweetEntity>();
		}
		
		return fullTweetContext;
	}

	/**
	 * @param fullTweetContext the fullTweetContext to set
	 */
	public void setFullTweetContext(Set<TweetEntity> fullTweetContext) {
		this.fullTweetContext = fullTweetContext;
	}
	
}
