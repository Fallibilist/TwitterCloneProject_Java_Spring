/**
 * 
 */
package com.cooksys.twitterclone.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.cooksys.twitterclone.dto.UserDeleteDto;
import com.cooksys.twitterclone.dto.UserGetDto;
import com.cooksys.twitterclone.dto.UserSaveDto;
import com.cooksys.twitterclone.entity.UserEntity;

/**
 * @author Greg Hill
 *
 */
@Mapper(componentModel="spring")
public interface UserMapper {

	@Mappings({
		@Mapping(source = "credentials.username", target = "username")
	})
	UserGetDto toDtoGet(UserEntity userEntity);
	
	default UserEntity fromDtoSave(UserSaveDto userSaveDto, ProfileMapper profileMapper, CredentialsMapper credentialsMapper) {
        UserEntity user = new UserEntity();
        user.setCredentials(credentialsMapper.fromDto(userSaveDto.getCredentials()));
        user.setProfile(profileMapper.fromDto(userSaveDto.getProfile()));
        
        return user;
    }
	
	UserEntity fromDtoDelete(UserDeleteDto userDeleteDto);
	
	UserDeleteDto toDtoDelete(UserEntity userEntity);
	
	Set<UserGetDto> toDto(Set<UserEntity> users);
	
}
