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
	
	private Utilities() { }
	
	public static Timestamp currentTime() {
		return new Timestamp(System.currentTimeMillis());
	}
}
