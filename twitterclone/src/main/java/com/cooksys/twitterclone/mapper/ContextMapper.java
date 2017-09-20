/**
 * 
 */
package com.cooksys.twitterclone.mapper;

import org.mapstruct.Mapper;

import com.cooksys.twitterclone.dto.ContextDto;
import com.cooksys.twitterclone.entity.ContextEntity;

/**
 * @author Greg Hill
 *
 */
@Mapper(componentModel="spring")
public interface ContextMapper {
	
	ContextEntity fromDto(ContextDto dto);
	
	ContextDto toDto(ContextEntity entity);
	
}
