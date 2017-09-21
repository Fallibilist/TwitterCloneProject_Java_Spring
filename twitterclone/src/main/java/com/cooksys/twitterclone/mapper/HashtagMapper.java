/**
 * 
 */
package com.cooksys.twitterclone.mapper;

import java.sql.Timestamp;
import java.util.TreeSet;

import org.mapstruct.Mapper;

import com.cooksys.twitterclone.dto.HashtagGetDto;
import com.cooksys.twitterclone.entity.HashtagEntity;

/**
 * @author Greg Hill
 *
 */
@Mapper(componentModel="spring")
public interface HashtagMapper {
	
	HashtagGetDto toDtoGet(HashtagEntity entity);

	TreeSet<HashtagGetDto> toDto(TreeSet<HashtagEntity> set);
	
	default Long timestampToLong(Timestamp timestamp) {
		return timestamp.getTime();
	}
	
}
