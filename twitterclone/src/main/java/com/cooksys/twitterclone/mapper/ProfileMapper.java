/**
 * 
 */
package com.cooksys.twitterclone.mapper;

import org.mapstruct.Mapper;

import com.cooksys.twitterclone.dto.ProfileDto;
import com.cooksys.twitterclone.entity.embeddable.ProfileEmbeddable;

/**
 * @author Greg Hill
 *
 */
@Mapper(componentModel="spring")
public interface ProfileMapper {

	ProfileEmbeddable fromDto(ProfileDto profileDto);
	
	ProfileDto toDto(ProfileEmbeddable profileEmbeddable);
	
}
