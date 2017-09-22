/**
 * 
 */
package com.cooksys.twitterclone.dto;

/**
 * @author Greg Hill
 *
 */
public class HashtagGetDto implements Comparable<HashtagGetDto>{

	private String label;
	
	private Long firstUsed;
	
	private Long lastUsed;
	
	/**
	 * Default Constructor
	 */
	public HashtagGetDto() { }
	
	/**
	 * @param label
	 * @param firstUsed
	 * @param lastUsed
	 */
	public HashtagGetDto(String label, Long firstUsed, Long lastUsed) {
		this();
		this.label = label;
		this.firstUsed = firstUsed;
		this.lastUsed = lastUsed;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the firstUsed
	 */
	public Long getFirstUsed() {
		return firstUsed;
	}

	/**
	 * @param firstUsed the firstUsed to set
	 */
	public void setFirstUsed(Long firstUsed) {
		this.firstUsed = firstUsed;
	}

	/**
	 * @return the lastUsed
	 */
	public Long getLastUsed() {
		return lastUsed;
	}

	/**
	 * @param lastUsed the lastUsed to set
	 */
	public void setLastUsed(Long lastUsed) {
		this.lastUsed = lastUsed;
	}

	@Override
	public int compareTo(HashtagGetDto tagToCompare) {
		return this.getLabel().compareTo(tagToCompare.getLabel());
	}
	
}
