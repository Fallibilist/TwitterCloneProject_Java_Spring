/**
 * 
 */
package com.cooksys.twitterclone.dto;

import java.sql.Timestamp;

/**
 * @author Greg Hill
 *
 */
public class UserGetDto {

	private String username;
	private Timestamp joined;
	private ProfileGetDto profileGetDto;

	/**
	 * Default Constructor
	 */
	public UserGetDto() { }

	/**
	 * @param username
	 * @param joined
	 */
	public UserGetDto(String username, Timestamp joined, ProfileGetDto profileGetDto) {
		this();
		this.username = username;
		this.joined = joined;
		this.profileGetDto = profileGetDto;
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
	 * @return the profileGetDto
	 */
	public ProfileGetDto getProfileGetDto() {
		return profileGetDto;
	}

	/**
	 * @param profileGetDto the profileGetDto to set
	 */
	public void setProfileGetDto(ProfileGetDto profileGetDto) {
		this.profileGetDto = profileGetDto;
	}
	
}
