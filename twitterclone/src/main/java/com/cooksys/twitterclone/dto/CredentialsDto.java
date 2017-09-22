/**
 * 
 */
package com.cooksys.twitterclone.dto;

/**
 * @author Greg Hill
 *
 */
public class CredentialsDto {

	private String username;
	
	private String password;
	
	/**
	 * Default Constructor
	 */
	public CredentialsDto() { }

	/**
	 * @param username
	 * @param password
	 */
	public CredentialsDto(String username, String password) {
		this();
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
