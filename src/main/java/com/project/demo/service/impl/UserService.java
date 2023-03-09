package com.project.demo.service.impl;

import com.project.demo.dto.UserGetDto;
import com.project.demo.dto.UserPostDto;
import com.project.demo.dto.UserPostUpdateDto;
import com.project.demo.exception.BadRequest;
import com.project.demo.exception.Forbidden;
import com.project.demo.mapper.UserMapper;
import com.project.demo.model.Role;
import com.project.demo.model.User;
import com.project.demo.repository.UserRepository;
import com.project.demo.service.IRoleService;
import com.project.demo.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.project.demo.util.Contants.Roles.ROLE_ADMIN;
import static com.project.demo.util.Contants.Roles.ROLE_USER;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService, UserDetailsService {

    private final UserRepository repository;
    private final IRoleService roleService;
    private final UserMapper mapper;
    private final MessageSource message;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public UserGetDto registerUser(UserPostDto dto) {
        if(repository.existsByUsername(dto.getUsername())) throw new BadRequest(
                message.getMessage("user.exists", null, Locale.US));
        User user = mapper.dtoToUser(dto);
        user.setPassword(encoder.encode(dto.getPassword()));
        Role role =  roleService.getByName(ROLE_USER);
        user.getRoles().add(role);
        User savedUser = repository.save(user);
        role.getUsers().add(savedUser);
        return mapper.userToDto(savedUser);
    }

    @Override
    @Transactional
    public UserGetDto loadUserData(UserPostDto dto) {
        User user = mapper.dtoToUser(dto);
        user.setPassword(encoder.encode(dto.getPassword()));
        Role role =  roleService.getByName(ROLE_ADMIN);
        user.getRoles().add(role);
        User savedUser = repository.save(user);
        role.getUsers().add(savedUser);
        return mapper.userToDto(savedUser);
    }

    @Override
    @Transactional
    public UserGetDto updateUser(UserPostUpdateDto dto, Long id, User loggedUser) {
        User user = getById(id);
        if (!loggedUser.getUsername().equals(user.getUsername())) throw new Forbidden(
                message.getMessage("access",null,Locale.US));
        if(!Objects.isNull(dto.getOldPassword()) && !dto.getOldPassword().isEmpty())
            if(!Objects.isNull(dto.getNewPassword())
                    && !dto.getNewPassword().isEmpty()
                    && encoder.matches(dto.getOldPassword(), loggedUser.getPassword()))
                user.setPassword(encoder.encode(dto.getNewPassword()));
        User savedUser = repository.save(mapper.updateUserFromDto(dto, user));
        return mapper.userToDto(user);
    }

    @Override
    public User getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequest(
                message.getMessage("user.notFound", null, Locale.US)));
    }

    @Override
    public UserGetDto getUserById(Long id) {
        return mapper.userToDto(getById(id));
    }

    @Override
    public List<UserGetDto> getAllUsers() {
        List<User> users = repository.findAll();
        return mapper.usersToDtos(users);
    }

    @Override
    @Transactional
    public void deleteUser(Long id, User loggedUser) {
        User user = getById(id);
        if (!loggedUser.getUsername().equals(user.getUsername())) throw new Forbidden(
                message.getMessage("access",null,Locale.US));
        repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found."));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


}
