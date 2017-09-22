/**
 * 
 */
package com.cooksys.twitterclone.dto;

import java.util.TreeSet;

/**
 * @author Greg Hill
 *
 */
public class ContextDto {
	
	private TweetGetDto target;
	
	private TreeSet<TweetGetDto> before;
	
	private TreeSet<TweetGetDto> after;

	/**
	 * Default Constructor
	 */
	public ContextDto() { }

	/**
	 * @param target
	 * @param before
	 * @param after
	 */
	public ContextDto(TweetGetDto target, TreeSet<TweetGetDto> before, TreeSet<TweetGetDto> after) {
		this();
		this.target = target;
		this.before = before;
		this.after = after;
	}

	/**
	 * @return the target
	 */
	public TweetGetDto getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(TweetGetDto target) {
		this.target = target;
	}

	/**
	 * @return the before
	 */
	public TreeSet<TweetGetDto> getBefore() {
		return before;
	}

	/**
	 * @param before the before to set
	 */
	public void setBefore(TreeSet<TweetGetDto> before) {
		this.before = before;
	}

	/**
	 * @return the after
	 */
	public TreeSet<TweetGetDto> getAfter() {
		return after;
	}

	/**
	 * @param after the after to set
	 */
	public void setAfter(TreeSet<TweetGetDto> after) {
		this.after = after;
	}
	
}
