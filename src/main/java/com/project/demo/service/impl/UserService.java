package com.project.demo.service.impl;

import com.project.demo.dto.UserGetDto;
import com.project.demo.dto.UserPostDto;
import com.project.demo.exception.BadRequest;
import com.project.demo.exception.Forbidden;
import com.project.demo.mapper.UserMapper;
import com.project.demo.model.Role;
import com.project.demo.model.User;
import com.project.demo.repository.RoleRepository;
import com.project.demo.repository.UserRepository;
import com.project.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private MessageSource message;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    @Transactional
    public UserGetDto registerUser(UserPostDto dto) {
        if(repository.existsByUsername(dto.getUsername())) throw new BadRequest(
                message.getMessage("user.exists", null, Locale.US));
        User user = mapper.dtoToUser(dto);
        user.setPassword(encoder.encode(dto.getPassword()));
        User savedUser = repository.save(user);
        addRoleToUser(ROLE_USER, savedUser);
        return mapper.userToDto(savedUser);
    }

    @Override
    @Transactional
    public UserGetDto loadUserData(UserPostDto dto) {
        User user = mapper.dtoToUser(dto);
        user.setPassword(encoder.encode(dto.getPassword()));
        User savedUser = repository.save(user);
        addRoleToUser(ROLE_ADMIN, savedUser);
        return mapper.userToDto(savedUser);
    }


    @Transactional
    private void addRoleToUser(String nameRole, User user) {
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByName(nameRole).get();
        roles.add(role);
        user.setRoles(roles);

        role.getUsers().add(user);
    }

    @Override
    @Transactional
    public UserGetDto updateUser(UserPostDto dto, Long id) {
        if(!findById(id).isPresent()) throw new BadRequest(
                message.getMessage("user.notFound", null, Locale.US));
        String username = findById(id).get().getUsername();
        String logged = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!logged.equals(username)) throw new Forbidden(
                message.getMessage("mismatch.users",null,Locale.US));
        User user = repository.save(mapper.updateUserFromDto(dto, findById(id).get()));
        return mapper.userToDto(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public UserGetDto getById(Long id) {
        if(!findById(id).isPresent()) throw new BadRequest(
                message.getMessage("user.notFound", null, Locale.US));
        String username = findById(id).get().getUsername();
        String logged = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!logged.equals(username)) throw new Forbidden(
                message.getMessage("mismatch.users",null,Locale.US));
        User user = findById(id).get();
        return mapper.userToDto(user);
    }

    @Override
    public List<UserGetDto> getAllUsers() {
        List<User> users = repository.findAll();
        return mapper.usersToDtos(users);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if(!findById(id).isPresent()) throw new BadRequest(
                message.getMessage("user.notFound", null, Locale.US));
        String username = findById(id).get().getUsername();
        String logged = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!logged.equals(username)) throw new Forbidden(
                message.getMessage("mismatch.users",null,Locale.US));
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
