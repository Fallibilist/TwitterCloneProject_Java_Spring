/**
 * 
 */
package com.cooksys.twitterclone.entity;

import java.sql.Timestamp;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * @author Greg Hill
 *
 */
@Entity
public class HashtagEntity implements Comparable<HashtagEntity>{

	@Id
	@GeneratedValue
	private Integer id;

	@Column(nullable = false)
	private String label;

	@Column(nullable = false)
	private Timestamp firstUsed;
	
	@Column(nullable = false)
	private Timestamp lastUsed;
	
	@ManyToMany(mappedBy = "hashtags")
	private Set<TweetEntity> tweets;

	/**
	 * Default Constructor
	 */
	public HashtagEntity() { }

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
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the firstUsed
	 */
	public Timestamp getFirstUsed() {
		return firstUsed;
	}

	/**
	 * @param firstUsed the firstUsed to set
	 */
	public void setFirstUsed(Timestamp firstUsed) {
		this.firstUsed = firstUsed;
	}

	/**
	 * @return the lastUsed
	 */
	public Timestamp getLastUsed() {
		return lastUsed;
	}

	/**
	 * @param lastUsed the lastUsed to set
	 */
	public void setLastUsed(Timestamp lastUsed) {
		this.lastUsed = lastUsed;
	}

	/**
	 * @return the tweets
	 */
	public Set<TweetEntity> getTweets() {
		if(tweets == null) {
			tweets = new TreeSet<TweetEntity>();
		}
		return tweets;
	}

	/**
	 * @param tweets the tweets to set
	 */
	public void setTweets(Set<TweetEntity> tweets) {
		this.tweets = tweets;
	}

	@Override
	public int compareTo(HashtagEntity tagToCompare) {
		return this.getLabel().compareTo(tagToCompare.getLabel());
	}
	
}
