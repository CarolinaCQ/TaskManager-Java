package com.project.demo.service.impl;

import com.project.demo.dto.RoleDto;
import com.project.demo.exception.BadRequest;
import com.project.demo.mapper.RoleMapper;
import com.project.demo.model.Role;
import com.project.demo.repository.RoleRepository;
import com.project.demo.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository repository;
    private final RoleMapper mapper;
    private final MessageSource message;

    @Override
    @Transactional
    public RoleDto createRole(RoleDto dto) {
        Role role = mapper.dtoToRole(dto);
        Role savedRole = repository.save(role);
        return mapper.roleToDto(savedRole);
    }

    @Override
    public void loadRoleData(RoleDto dto) {
        Role role = mapper.dtoToRole(dto);
        repository.save(role);
    }

    @Override
    @Transactional
    public RoleDto updateRole(RoleDto dto, Long id) {
        Role role = getById(id);
        Role savedRole = mapper.updateRoleFromDto(dto, role);
        return mapper.roleToDto(savedRole);
    }

    @Override
    public Role getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequest(
                message.getMessage("role.notFound", null, Locale.US)));
    }

    @Override
    public Role getByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new BadRequest(
                message.getMessage("role.notFound", null, Locale.US)));
    }

    @Override
    public RoleDto getRoleById(Long id) {
        Role role = getById(id);
        return mapper.roleToDto(role);
    }

    @Override
    public void deleteRole(Long id) {
        Role role = getById(id);
        repository.deleteById(id);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = repository.findAll();
        return mapper.rolesToDtos(roles);
    }
}
