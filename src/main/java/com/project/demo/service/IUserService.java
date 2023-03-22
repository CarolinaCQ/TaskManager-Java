package com.project.demo.service;

import com.project.demo.dto.*;
import com.project.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    UserGetDto registerUser (UserPostDto dto);
    UserGetDto loadUserData (UserPostDto dto);
    LoginResponseDto loginUser(LoginRequestDto dto);
    UserGetDto updateUser(UserPostUpdateDto dto, Long id, User loggedUser);
    User getById(Long id);
    UserGetDto getUserById(Long id);
    List<UserGetDto> getAllUsers();
    void deleteUser(Long id, User loggedUser);
}
