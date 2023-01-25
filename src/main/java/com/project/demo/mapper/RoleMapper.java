package com.project.demo.mapper;

import com.project.demo.dto.RoleDto;
import com.project.demo.model.Role;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role dtoToRole (RoleDto dto);

    RoleDto roleToDto (Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Role updateRoleFromDto(RoleDto dto, @MappingTarget Role role);

    List<RoleDto> rolesToDtos (List<Role> roles);

}
