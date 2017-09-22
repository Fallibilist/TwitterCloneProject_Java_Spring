/**
 * 
 */
package com.cooksys.twitterclone.dto;

/**
 * @author Greg Hill
 *
 */
public class TweetSaveDto {

	private String content;
	
	private CredentialsDto credentials;
	
	/**
	 * Default Constructor
	 */
	public TweetSaveDto() { }
	
	/**
	 * @param content
	 * @param credentialsDto
	 */
	public TweetSaveDto(String content, CredentialsDto credentials) {
		this();
		this.content = content;
		this.credentials = credentials;
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
	
	/**
	 * @return the credentialsDto
	 */
	public CredentialsDto getCredentials() {
		return credentials;
	}
	
	/**
	 * @param credentialsDto the credentialsDto to set
	 */
	public void setCredentials(CredentialsDto credentials) {
		this.credentials = credentials;
	}
	
}
