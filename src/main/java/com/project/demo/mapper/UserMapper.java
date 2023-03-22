package com.project.demo.mapper;

import com.project.demo.dto.UserGetDto;
import com.project.demo.dto.UserPostDto;
import com.project.demo.dto.UserPostUpdateDto;
import com.project.demo.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToUser (UserPostDto dto);

    UserGetDto userToDto (User user);

    User dtoToUser (UserGetDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromDto (UserPostUpdateDto dto, @MappingTarget User user);

    List<UserGetDto> usersToDtos (List<User> users);
}
