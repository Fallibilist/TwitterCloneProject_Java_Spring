/**
 * 
 */
package com.cooksys.twitterclone.entity;

import java.sql.Timestamp;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.cooksys.twitterclone.dto.UserGetDto;
import com.cooksys.twitterclone.entity.embeddable.CredentialsEmbeddable;
import com.cooksys.twitterclone.entity.embeddable.ProfileEmbeddable;

/**
 * @author Greg Hill
 *
 */
@Entity
public class UserEntity implements Comparable<UserEntity>{

	@Id
	@GeneratedValue
	private Integer id;

	@Column(nullable = false)
	@Embedded
	private CredentialsEmbeddable credentials;

	@Column(nullable = false)
	@Embedded
	private ProfileEmbeddable profile;
	
	@Column(nullable = false)
	private Timestamp joined;
	
	@OneToMany(mappedBy = "author")
	private Set<TweetEntity> tweets;
	
	private Boolean active;

	/**
	 * Default Constructor
	 */
	public UserEntity() {
		this.active = true;
	}

	/**
	 * @param id
	 * @param credentials
	 * @param profile
	 * @param joined
	 * @param tweets
	 */
	public UserEntity(Integer id, CredentialsEmbeddable credentials, ProfileEmbeddable profile, Timestamp joined,
			TreeSet<TweetEntity> tweets) {
		this();
		this.id = id;
		this.credentials = credentials;
		this.profile = profile;
		this.joined = joined;
		this.tweets = tweets;
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
	 * @return the credentials
	 */
	public CredentialsEmbeddable getCredentials() {
		return credentials;
	}

	/**
	 * @param credentials the credentials to set
	 */
	public void setCredentials(CredentialsEmbeddable credentials) {
		this.credentials = credentials;
	}

	/**
	 * @return the profile
	 */
	public ProfileEmbeddable getProfile() {
		return profile;
	}

	/**
	 * @param profile the profile to set
	 */
	public void setProfile(ProfileEmbeddable profile) {
		this.profile = profile;
	}

	/**
	 * @return the joined
	 */
	public Timestamp getJoined() {
		return joined;
	}

	/**
	 * @param joined the joined to set
	 */
	public void setJoined(Timestamp joined) {
		this.joined = joined;
	}

	/**
	 * @return the tweets
	 */
	public TreeSet<TweetEntity> getTweets() {
		return (TreeSet<TweetEntity>)tweets;
	}

	/**
	 * @param tweets the tweets to set
	 */
	public void setTweets(TreeSet<TweetEntity> tweets) {
		this.tweets = tweets;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @param tweets the tweets to set
	 */
	public void setTweets(Set<TweetEntity> tweets) {
		this.tweets = tweets;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserEntity other = (UserEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(UserEntity userToCompareTo) {
		return credentials.getUsername().compareTo(userToCompareTo.getCredentials().getUsername());
	}
	
}
