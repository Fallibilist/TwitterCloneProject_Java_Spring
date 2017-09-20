/**
 * 
 */
package com.cooksys.twitterclone.dto;

/**
 * @author Greg Hill
 *
 */
public class UserSaveDto {

	private CredentialsDto credentialsDto;
	private ProfileDto profileDto;
	
	/**
	 * Default Constructor
	 */
	public UserSaveDto() { }

	/**
	 * @param credentials
	 * @param profileSaveDto
	 */
	public UserSaveDto(CredentialsDto credentialsDto, ProfileDto profileDto) {
		this();
		this.credentialsDto = credentialsDto;
		this.profileDto = profileDto;
	}

	/**
	 * @return the credentials
	 */
	public CredentialsDto getCredentialsDto() {
		return credentialsDto;
	}

	/**
	 * @param credentials the credentials to set
	 */
	public void setCredentialsDto(CredentialsDto credentialsDto) {
		this.credentialsDto = credentialsDto;
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
	
}
