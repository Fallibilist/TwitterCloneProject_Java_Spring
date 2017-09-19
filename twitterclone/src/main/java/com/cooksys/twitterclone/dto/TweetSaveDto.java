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
	private CredentialsDto credentialsDto;
	
	/**
	 * Default Constructor
	 */
	public TweetSaveDto() { }
	
	/**
	 * @param content
	 * @param credentialsDto
	 */
	public TweetSaveDto(String content, CredentialsDto credentialsDto) {
		this();
		this.content = content;
		this.credentialsDto = credentialsDto;
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
	public CredentialsDto getCredentialsDto() {
		return credentialsDto;
	}
	
	/**
	 * @param credentialsDto the credentialsDto to set
	 */
	public void setCredentialsDto(CredentialsDto credentialsDto) {
		this.credentialsDto = credentialsDto;
	}
	
}
