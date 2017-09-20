/**
 * 
 */
package com.cooksys.twitterclone.mapper;

import org.mapstruct.Mapper;

import com.cooksys.twitterclone.dto.TweetGetDto;
import com.cooksys.twitterclone.dto.TweetSaveDto;
import com.cooksys.twitterclone.entity.TweetEntity;

/**
 * @author Greg Hill
 *
 */
@Mapper(componentModel="spring")
public interface TweetMapper {
	
	TweetEntity fromDtoGet(TweetGetDto tweetGetDto);
	
	TweetGetDto fromEntityGet(TweetEntity tweetEntity);
	
	TweetEntity fromDtoSave(TweetSaveDto tweetGetDto);
	
	TweetSaveDto fromEntitySave(TweetEntity tweetEntity);
	
}
