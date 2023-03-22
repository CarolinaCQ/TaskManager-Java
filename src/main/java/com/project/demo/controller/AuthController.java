package com.project.demo.controller;

import com.project.demo.dto.*;
import com.project.demo.model.User;
import com.project.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
public class AuthController {

    @Autowired
    private IUserService service;

    @PostMapping("auth/register")
    public ResponseEntity<UserGetDto> registerUser(@RequestBody @Valid UserPostDto dto){
        UserGetDto user = service.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("auth/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto){
        LoginResponseDto response = service.loginUser(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<UserGetDto> getUserById(@PathVariable Long id){
        UserGetDto user = service.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("users")
    public ResponseEntity<List<UserGetDto>> getAllUsers(){
        List<UserGetDto> users= service.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PatchMapping("users/{id}")
    public ResponseEntity<UserGetDto> updateUser(@RequestBody @Valid UserPostUpdateDto dto, @PathVariable Long id, @AuthenticationPrincipal User loggedUser){
        UserGetDto user = service.updateUser(dto, id, loggedUser);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @AuthenticationPrincipal User loggedUser){
        service.deleteUser(id, loggedUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
