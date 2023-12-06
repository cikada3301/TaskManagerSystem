package com.example.taskmanagersystemapi.mapper;

import com.example.taskmanagersystemapi.dto.UserRegistrationDto;
import com.example.taskmanagersystemapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto);
}
