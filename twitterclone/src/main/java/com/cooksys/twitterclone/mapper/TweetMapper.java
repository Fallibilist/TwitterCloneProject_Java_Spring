/**
 * 
 */
package com.cooksys.twitterclone.mapper;

import java.sql.Timestamp;
import java.util.TreeSet;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.twitterclone.dto.TweetGetDto;
import com.cooksys.twitterclone.dto.TweetRepostDto;
import com.cooksys.twitterclone.dto.TweetSaveDto;
import com.cooksys.twitterclone.dto.UserGetDto;
import com.cooksys.twitterclone.entity.TweetEntity;
import com.cooksys.twitterclone.entity.UserEntity;

/**
 * @author Greg Hill
 *
 */
@Mapper(componentModel="spring")
public interface TweetMapper {

	TweetGetDto toDtoGet(TweetEntity tweet);
	
	@Mapping(source = "credentials.username", target = "username")
	UserGetDto toUserDtoGet(UserEntity user);
	
	TweetEntity fromDtoSave(TweetSaveDto tweetGetDto);

	TreeSet<TweetGetDto> toDto(TreeSet<TweetEntity> allTweets);

	TweetRepostDto toDtoRepost(TweetEntity tweet);
	
	default Long timestampToLong(Timestamp timestamp) {
		return timestamp.getTime();
	}
	
}
