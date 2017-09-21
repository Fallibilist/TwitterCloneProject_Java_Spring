/**
 * 
 */
package com.cooksys.twitterclone.mapper;

import java.sql.Timestamp;
import java.util.Set;
import java.util.TreeSet;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.cooksys.twitterclone.dto.TweetGetDto;
import com.cooksys.twitterclone.dto.TweetRepostDto;
import com.cooksys.twitterclone.dto.TweetSaveDto;
import com.cooksys.twitterclone.entity.TweetEntity;

/**
 * @author Greg Hill
 *
 */
@Mapper(componentModel="spring")
public interface TweetMapper {

	TweetGetDto toDtoGet(TweetEntity tweet);
	
	TweetEntity fromDtoSave(TweetSaveDto tweetGetDto);

	TreeSet<TweetGetDto> toDto(TreeSet<TweetEntity> allTweets);

	TweetRepostDto toDtoRepost(TweetEntity tweet);
	
	default Long timestampToLong(Timestamp timestamp) {
		return timestamp.getTime();
	}
	
}
