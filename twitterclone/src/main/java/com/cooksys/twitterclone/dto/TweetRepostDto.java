/**
 * 
 */
package com.cooksys.twitterclone.dto;

/**
 * @author Greg Hill
 *
 */
public class TweetRepostDto {

	private Integer id;
	
	private UserGetDto author;
	
	private Long posted;
	
	private TweetGetDto repostOf;

	/**
	 * Default Constructor
	 */
	public TweetRepostDto() {
	}

	/**
	 * @param id
	 * @param author
	 * @param posted
	 * @param repostOf
	 */
	public TweetRepostDto(Integer id, UserGetDto author, Long posted, TweetGetDto repostOf) {
		this();
		this.id = id;
		this.author = author;
		this.posted = posted;
		this.repostOf = repostOf;
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
	public UserGetDto getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(UserGetDto author) {
		this.author = author;
	}

	/**
	 * @return the posted
	 */
	public Long getPosted() {
		return posted;
	}

	/**
	 * @param posted the posted to set
	 */
	public void setPosted(Long posted) {
		this.posted = posted;
	}

	/**
	 * @return the repostOf
	 */
	public TweetGetDto getRepostOf() {
		return repostOf;
	}

	/**
	 * @param repostOf the repostOf to set
	 */
	public void setRepostOf(TweetGetDto repostOf) {
		this.repostOf = repostOf;
	}
	
}
