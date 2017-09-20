/**
 * 
 */
package com.cooksys.twitterclone.mapper;

import org.mapstruct.Mapper;

import com.cooksys.twitterclone.dto.HashtagGetDto;
import com.cooksys.twitterclone.entity.HashtagEntity;

/**
 * @author Greg Hill
 *
 */
@Mapper(componentModel="spring")
public interface HashtagMapper {
	
	HashtagEntity fromDto(HashtagGetDto dto);
	
	HashtagGetDto toDto(HashtagEntity entity);
	
}
