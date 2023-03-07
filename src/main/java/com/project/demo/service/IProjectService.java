package com.project.demo.service;

import com.project.demo.dto.ProjectDto;
import com.project.demo.model.Project;
import com.project.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IProjectService {

    ProjectDto createProject(ProjectDto dto, User loggedUser);
    ProjectDto updateProject(ProjectDto dto, Long id, User loggedUser);
    Project getById(Long id);
    ProjectDto getProjectById(Long id);
    List<ProjectDto> getAllProjectsByUserId(Long userId);
    void deleteProject(Long id, User loggedUser);
    Page<Project> getProjectPage(Integer numberPage, Pageable pageable, Long userId);
    Map<String, Object> responseProjectPage(Integer numberPage, Pageable pageable, Long userId);

}
