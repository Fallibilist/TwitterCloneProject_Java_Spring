/**
 * 
 */
package com.cooksys.twitterclone.entity.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Greg Hill
 *
 */
@Embeddable
public class CredentialsEmbeddable {

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	/**
	 * Default Constructor
	 */
	public CredentialsEmbeddable() { }

	/**
	 * @param username
	 * @param password
	 */
	public CredentialsEmbeddable(String username, String password) {
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
