/**
 * 
 */
package com.cooksys.twitterclone.mapper;

import org.mapstruct.Mapper;

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
	
	UserEntity fromDtoGet(UserGetDto userGetDto);
	
	UserGetDto toDtoGet(UserEntity userEntity);
	
	UserEntity fromDtoSave(UserSaveDto userSaveDto);
	
	UserSaveDto toDtoSave(UserEntity userEntity);
	
	UserEntity fromDtoDelete(UserDeleteDto userDeleteDto);
	
	UserDeleteDto toDtoDelete(UserEntity userEntity);
	
}
