/**
 * 
 */
package com.cooksys.twitterclone.mapper;

import java.sql.Timestamp;
import java.util.TreeSet;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.twitterclone.dto.CredentialsDto;
import com.cooksys.twitterclone.dto.ProfileDto;
import com.cooksys.twitterclone.dto.UserGetDto;
import com.cooksys.twitterclone.dto.UserSaveDto;
import com.cooksys.twitterclone.entity.UserEntity;
import com.cooksys.twitterclone.entity.embeddable.CredentialsEmbeddable;
import com.cooksys.twitterclone.entity.embeddable.ProfileEmbeddable;

/**
 * @author Greg Hill
 *
 */
@Mapper(componentModel="spring")
public interface UserMapper {

	@Mapping(source = "credentials.username", target = "username")
	UserGetDto toDtoGet(UserEntity userEntity);
//	
//	default UserEntity fromDtoSave(UserSaveDto userSaveDto, ProfileMapper profileMapper, CredentialsMapper credentialsMapper) {
//        UserEntity user = new UserEntity();
//        user.setCredentials(credentialsMapper.fromDto(userSaveDto.getCredentials()));
//        user.setProfile(profileMapper.fromDto(userSaveDto.getProfile()));
//        
//        return user;
//    }
	UserEntity fromDtoSave(UserSaveDto userSaveDto);
	
	CredentialsEmbeddable fromDto(CredentialsDto credentials);
	
	ProfileEmbeddable fromDto(ProfileDto profile);
	
	TreeSet<UserGetDto> toDto(TreeSet<UserEntity> users);
	
	default Long timestampToLong(Timestamp timestamp) {
		return timestamp.getTime();
	}
	
}
