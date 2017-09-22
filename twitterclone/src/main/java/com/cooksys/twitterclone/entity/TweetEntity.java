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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * @author Greg Hill
 *
 */
@Entity
public class TweetEntity implements Comparable<TweetEntity> {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private UserEntity author;

	@Column(nullable = false)
	private Timestamp posted;
	
	private String content;

    @OneToOne
    @JoinColumn(name="in_reply_to_id")
	private TweetEntity inReplyTo;

    @OneToOne
    @JoinColumn(name="repost_of_id")
	private TweetEntity repostOf;
	
    @ManyToOne
    private TweetEntity parent;
    
	@OneToMany(mappedBy = "parent")
	private Set<TweetEntity> directChildren;
	
	@ManyToMany
	private Set<UserEntity> mentionedUsers;
	
	@ManyToMany
	private Set<HashtagEntity> hashtags;
	
	@ManyToMany
	private Set<UserEntity> likes;
	
	@ManyToOne
	private UserEntity deactivatedUser;

	private Boolean active;
	
	/**
	 * Default Constructor
	 */
	public TweetEntity() {
		this.active = true;
	}

	/**
	 * @param id
	 * @param author
	 * @param posted
	 */
	public TweetEntity(Integer id, UserEntity author, Timestamp posted) {
		this();
		this.id = id;
		this.author = author;
		this.posted = posted;
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
	 * @return the author
	 */
	public UserEntity getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(UserEntity author) {
		this.author = author;
	}

	/**
	 * @return the posted
	 */
	public Timestamp getPosted() {
		return posted;
	}

	/**
	 * @param posted the posted to set
	 */
	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		if(repostOf != null) {
			return repostOf.getContent();
		} else {
			return content;
		}
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the inReplyTo
	 */
	public TweetEntity getInReplyTo() {
		return inReplyTo;
	}

	/**
	 * @param inReplyTo the inReplyTo to set
	 */
	public void setInReplyTo(TweetEntity inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	/**
	 * @return the repostOf
	 */
	public TweetEntity getRepostOf() {
		return repostOf;
	}

	/**
	 * @param repostOf the repostOf to set
	 */
	public void setRepostOf(TweetEntity repostOf) {
		this.repostOf = repostOf;
	}

	/**
	 * @return the parent
	 */
	public TweetEntity getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(TweetEntity parent) {
		this.parent = parent;
	}

	/**
	 * @return the directChildren
	 */
	public Set<TweetEntity> getDirectChildren() {
		if(directChildren == null) {
			directChildren = new TreeSet<TweetEntity>();
		}
		return directChildren;
	}

	/**
	 * @param directChildren the directChildren to set
	 */
	public void setDirectChildren(Set<TweetEntity> directChildren) {
		this.directChildren = directChildren;
	}

	/**
	 * @return the mentionedUsers
	 */
	public Set<UserEntity> getMentionedUsers() {
		if(mentionedUsers == null) {
			mentionedUsers = new TreeSet<UserEntity>();
		}
		return mentionedUsers;
	}

	/**
	 * @param mentionedUsers the mentionedUsers to set
	 */
	public void setMentionedUsers(Set<UserEntity> mentionedUsers) {
		this.mentionedUsers = mentionedUsers;
	}

	/**
	 * @return the hashtags
	 */
	public Set<HashtagEntity> getHashtags() {
		if(hashtags == null) {
			hashtags = new TreeSet<HashtagEntity>();
		}
		return hashtags;
	}

	/**
	 * @param hashtags the hashtags to set
	 */
	public void setHashtags(Set<HashtagEntity> hashtags) {
		this.hashtags = hashtags;
	}
	
	/**
	 * @return the likes
	 */
	public Set<UserEntity> getLikes() {
		if(likes == null) {
			likes = new TreeSet<UserEntity>();
		}
		return likes;
	}

	/**
	 * @param likes the likes to set
	 */
	public void setLikes(Set<UserEntity> likes) {
		this.likes = likes;
	}

	/**
	 * @return the deactivatedUser
	 */
	public UserEntity getDeactivatedUser() {
		return deactivatedUser;
	}

	/**
	 * @param deactivatedUser the deactivatedUser to set
	 */
	public void setDeactivatedUser(UserEntity deactivatedUser) {
		this.deactivatedUser = deactivatedUser;
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
		TweetEntity other = (TweetEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(TweetEntity tweetToCompare) {
		if(this.id < tweetToCompare.getId()) {
			return -1;
		} else if(this.id > tweetToCompare.getId()) {
			return 1;
		} else {
			return 0;
		}
	}
}
