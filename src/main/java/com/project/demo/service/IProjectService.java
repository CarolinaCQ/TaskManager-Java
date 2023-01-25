package com.project.demo.service;

import com.project.demo.dto.ProjectDto;
import com.project.demo.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IProjectService {

    ProjectDto createProject(ProjectDto dto);
    void addUser (Project project);
    ProjectDto updateProject(ProjectDto dto, Long id);
    Optional<Project> findById(Long id);
    ProjectDto getById(Long id);
    List<ProjectDto> getAllProjectsByUserId(Long userId);
    void deleteProject(Long id);
    Page<Project> getProjectPage(Integer numberPage, Pageable pageable, Long userId);
    Map<String, Object> responseProjectPage(Integer numberPage, Pageable pageable, Long userId);

}
