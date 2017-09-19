/**
 * 
 */
package com.cooksys.twitterclone.dto;

import java.sql.Timestamp;

/**
 * @author Greg Hill
 *
 */
public class UserSaveDto {

	private CredentialsDto credentialsDto;
	private ProfileSaveDto profileSaveDto;
	
	/**
	 * Default Constructor
	 */
	public UserSaveDto() { }

	/**
	 * @param credentials
	 * @param profileSaveDto
	 */
	public UserSaveDto(CredentialsDto credentialsDto, ProfileSaveDto profileSaveDto) {
		this();
		this.credentialsDto = credentialsDto;
		this.profileSaveDto = profileSaveDto;
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
	 * @return the profileSaveDto
	 */
	public ProfileSaveDto getProfileSaveDto() {
		return profileSaveDto;
	}

	/**
	 * @param profileSaveDto the profileSaveDto to set
	 */
	public void setProfileSaveDto(ProfileSaveDto profileSaveDto) {
		this.profileSaveDto = profileSaveDto;
	}
	
}
