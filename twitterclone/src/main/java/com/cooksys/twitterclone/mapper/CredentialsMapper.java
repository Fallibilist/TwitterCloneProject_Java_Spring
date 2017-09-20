/**
 * 
 */
package com.cooksys.twitterclone.mapper;

import org.mapstruct.Mapper;

import com.cooksys.twitterclone.dto.CredentialsDto;
import com.cooksys.twitterclone.entity.embeddable.CredentialsEmbeddable;

/**
 * @author Greg Hill
 *
 */
@Mapper(componentModel="spring")
public interface CredentialsMapper {

	CredentialsEmbeddable fromDto(CredentialsDto credentialsDto);
	
	CredentialsDto toDto(CredentialsEmbeddable credentialsEmbeddable);
	
}
