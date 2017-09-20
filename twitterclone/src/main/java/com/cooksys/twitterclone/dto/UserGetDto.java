/**
 * 
 */
package com.cooksys.twitterclone.dto;

import java.sql.Timestamp;

/**
 * @author Greg Hill
 *
 */
public class UserGetDto implements Comparable<UserGetDto>{

	private String username;
	private Timestamp joined;
	private ProfileDto profileDto;

	/**
	 * Default Constructor
	 */
	public UserGetDto() { }

	/**
	 * @param username
	 * @param joined
	 */
	public UserGetDto(String username, Timestamp joined, ProfileDto profileDto) {
		this();
		this.username = username;
		this.joined = joined;
		this.profileDto = profileDto;
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
	 * @return the profileDto
	 */
	public ProfileDto getProfileDto() {
		return profileDto;
	}

	/**
	 * @param profileDto the profileDto to set
	 */
	public void setProfileDto(ProfileDto profileDto) {
		this.profileDto = profileDto;
	}

	@Override
	public int compareTo(UserGetDto userToCompareTo) {
		return username.compareTo(userToCompareTo.getUsername());
	}
	
}
