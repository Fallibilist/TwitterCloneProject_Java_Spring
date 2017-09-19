/**
 * 
 */
package com.cooksys.twitterclone.dto;

/**
 * @author Greg Hill
 *
 */
public class UserDeleteDto {

	private CredentialsDto credentialsDto;

	/**
	 * Default Constructor
	 */
	public UserDeleteDto() { }

	/**
	 * @param credentialsDto
	 */
	public UserDeleteDto(CredentialsDto credentialsDto) {
		this();
		this.credentialsDto = credentialsDto;
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
