/**
 * 
 */
package com.cooksys.twitterclone.dto;

/**
 * @author Greg Hill
 *
 */
public class UserGetDto implements Comparable<UserGetDto>{

	private String username;
	
	private Long joined;
	
	private ProfileDto profile;

	/**
	 * Default Constructor
	 */
	public UserGetDto() { }

	/**
	 * @param username
	 * @param joined
	 */
	public UserGetDto(String username, Long joined, ProfileDto profile) {
		this();
		this.username = username;
		this.joined = joined;
		this.profile = profile;
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
	public Long getJoined() {
		return joined;
	}

	/**
	 * @param joined the joined to set
	 */
	public void setJoined(Long joined) {
		this.joined = joined;
	}

	/**
	 * @return the profile
	 */
	public ProfileDto getProfile() {
		return profile;
	}

	/**
	 * @param profile the profile to set
	 */
	public void setProfile(ProfileDto profile) {
		this.profile = profile;
	}

	@Override
	public int compareTo(UserGetDto userToCompareTo) {
		return username.compareTo(userToCompareTo.getUsername());
	}
	
}
