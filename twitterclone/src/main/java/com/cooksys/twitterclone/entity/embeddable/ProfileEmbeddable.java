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
public class ProfileEmbeddable {
	
	private String firstName;
	
	private String lastName;

	@Column(nullable = false)
	private String email;
	
	private String phone;

	/**
	 * Default Constructor
	 */
	public ProfileEmbeddable() { }

	/**
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param phone
	 */
	public ProfileEmbeddable(String firstName, String lastName, String email, String phone) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
