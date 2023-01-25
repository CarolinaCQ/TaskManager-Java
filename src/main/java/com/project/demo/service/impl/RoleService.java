package com.project.demo.service.impl;

import com.project.demo.dto.RoleDto;
import com.project.demo.exception.BadRequest;
import com.project.demo.mapper.RoleMapper;
import com.project.demo.model.Role;
import com.project.demo.repository.RoleRepository;
import com.project.demo.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private RoleMapper mapper;

    @Autowired
    private MessageSource message;

    @Override
    @Transactional
    public RoleDto createRole(RoleDto dto) {
        Role role = mapper.dtoToRole(dto);
        role.setDeleted(false);
        Role savedRole = repository.save(role);
        return mapper.roleToDto(savedRole);
    }

    @Override
    public void loadRoleData(RoleDto dto) {
        Role role = mapper.dtoToRole(dto);
        role.setDeleted(false);
        repository.save(role);
    }

    @Override
    @Transactional
    public RoleDto updateRole(RoleDto dto, Long id) {
        if(!findById(id).isPresent()) throw new BadRequest(
                message.getMessage("role.notFound", null, Locale.US));
        Role role = mapper.updateRoleFromDto(dto, findById(id).get());
        return mapper.roleToDto(role);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public RoleDto getById(Long id) {
        if(!findById(id).isPresent()) throw new BadRequest(
                message.getMessage("role.notFound", null, Locale.US));
        Role role = findById(id).get();
        return mapper.roleToDto(role);
    }

    @Override
    public void deleteRole(Long id) {
        if(!findById(id).isPresent()) throw new BadRequest(
                message.getMessage("role.notFound", null, Locale.US));
        repository.deleteById(id);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = repository.findAll();
        return mapper.rolesToDtos(roles);
    }
}
