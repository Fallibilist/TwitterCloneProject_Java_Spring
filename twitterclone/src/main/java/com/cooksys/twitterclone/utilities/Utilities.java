/**
 * 
 */
package com.cooksys.twitterclone.utilities;

import java.sql.Timestamp;

/**
 * @author Greg Hill
 *
 */
public class Utilities {
	
	/**
	 * A private constructor so that no instances of the utilities class can be instantiated
	 */
	private Utilities() { }
	
	/**
	 * @return the current system time as a timestamp
	 */
	public static Timestamp currentTime() {
		return new Timestamp(System.currentTimeMillis());
	}
}
