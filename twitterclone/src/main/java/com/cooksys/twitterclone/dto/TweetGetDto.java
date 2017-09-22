/**
 * 
 */
package com.cooksys.twitterclone.dto;

/**
 * @author Greg Hill
 *
 */
public class TweetGetDto implements Comparable<TweetGetDto>{

	private Integer id;
	
	private UserGetDto author;
	
	private Long posted;
	
	private String content;
	
	/**
	 * Default Constructor
	 */
	public TweetGetDto() { }
	
	/**
	 * @param id
	 * @param author
	 * @param posted
	 * @param content
	 */
	public TweetGetDto(Integer id, UserGetDto author, Long posted, String content) {
		this();
		this.id = id;
		this.author = author;
		this.posted = posted;
		this.content = content;
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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
		TweetGetDto other = (TweetGetDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(TweetGetDto tweetToCompare) {
		if(this.id < tweetToCompare.getId()) {
			return -1;
		} else if(this.id > tweetToCompare.getId()) {
			return 1;
		} else {
			return 0;
		}
	}
	
}
