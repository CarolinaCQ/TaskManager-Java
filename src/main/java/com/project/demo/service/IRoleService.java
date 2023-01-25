package com.project.demo.service;

import com.project.demo.dto.ConditionDto;
import com.project.demo.dto.RoleDto;
import com.project.demo.model.Condition;
import com.project.demo.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    RoleDto createRole (RoleDto dto);
    void loadRoleData(RoleDto dto);
    RoleDto updateRole (RoleDto dto, Long id);
    Optional<Role> findById (Long id);
    RoleDto getById (Long id);
    void deleteRole (Long id);
    List<RoleDto> getAllRoles();
}
