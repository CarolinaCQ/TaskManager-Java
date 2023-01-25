package com.project.demo.service;

import com.project.demo.dto.UserGetDto;
import com.project.demo.dto.UserPostDto;
import com.project.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    UserGetDto registerUser (UserPostDto dto);
    UserGetDto loadUserData (UserPostDto dto);
    UserGetDto updateUser(UserPostDto dto, Long id);
    Optional<User> findById(Long id);
    UserGetDto getById(Long id);
    List<UserGetDto> getAllUsers();
    void deleteUser(Long id);
}
