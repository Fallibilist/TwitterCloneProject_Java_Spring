/**
 * 
 */
package com.cooksys.twitterclone.mapper;

import java.sql.Timestamp;
import java.util.TreeSet;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.twitterclone.dto.UserGetDto;
import com.cooksys.twitterclone.dto.UserSaveDto;
import com.cooksys.twitterclone.entity.UserEntity;

/**
 * @author Greg Hill
 *
 */
@Mapper(componentModel="spring")
public interface UserMapper {

	@Mapping(source = "credentials.username", target = "username")
	UserGetDto toDtoGet(UserEntity userEntity);
	
	default UserEntity fromDtoSave(UserSaveDto userSaveDto, ProfileMapper profileMapper, CredentialsMapper credentialsMapper) {
        UserEntity user = new UserEntity();
        user.setCredentials(credentialsMapper.fromDto(userSaveDto.getCredentials()));
        user.setProfile(profileMapper.fromDto(userSaveDto.getProfile()));
        
        return user;
    }
	
	TreeSet<UserGetDto> toDto(TreeSet<UserEntity> users);
	
	default Long timestampToLong(Timestamp timestamp) {
		return timestamp.getTime();
	}
	
}
