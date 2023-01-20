package com.project.demo.mapper;

import com.project.demo.dto.ProjectDto;
import com.project.demo.model.Project;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    Project dtoToProject(ProjectDto dto);

    ProjectDto projectToDto (Project project);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Project updateProjectFromDto(ProjectDto dto, @MappingTarget Project project);

    List<ProjectDto> projectsToDtos (List<Project> projects);

}
