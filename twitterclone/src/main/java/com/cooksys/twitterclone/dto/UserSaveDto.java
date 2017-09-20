/**
 * 
 */
package com.cooksys.twitterclone.dto;

/**
 * @author Greg Hill
 *
 */
public class UserSaveDto {

	private CredentialsDto credentials;
	
	private ProfileDto profile;
	
	/**
	 * Default Constructor
	 */
	public UserSaveDto() { }

	/**
	 * @param credentials
	 * @param profile
	 */
	public UserSaveDto(CredentialsDto credentials, ProfileDto profile) {
		this();
		this.credentials = credentials;
		this.profile = profile;
	}

	/**
	 * @return the credentials
	 */
	public CredentialsDto getCredentials() {
		return credentials;
	}

	/**
	 * @param credentials the credentials to set
	 */
	public void setCredentials(CredentialsDto credentials) {
		this.credentials = credentials;
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
	
}
