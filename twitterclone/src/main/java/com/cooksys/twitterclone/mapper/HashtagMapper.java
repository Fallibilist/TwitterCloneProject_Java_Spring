/**
 * 
 */
package com.cooksys.twitterclone.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;

import com.cooksys.twitterclone.dto.HashtagGetDto;
import com.cooksys.twitterclone.entity.HashtagEntity;

/**
 * @author Greg Hill
 *
 */
@Mapper(componentModel="spring")
public interface HashtagMapper {
	
	HashtagGetDto toDto(HashtagEntity entity);

	Set<HashtagGetDto> toDto(Set<HashtagEntity> set);
	
}
