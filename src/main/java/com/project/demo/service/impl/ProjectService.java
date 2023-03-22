package com.project.demo.service.impl;

import com.project.demo.dto.ProjectDto;
import com.project.demo.exception.BadRequest;
import com.project.demo.exception.Forbidden;
import com.project.demo.exception.NotFound;
import com.project.demo.mapper.ProjectMapper;
import com.project.demo.model.Project;
import com.project.demo.model.User;
import com.project.demo.repository.ProjectRepository;
import com.project.demo.service.IProjectService;
import com.project.demo.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.*;

import static com.project.demo.util.Contants.Page.URI_PAGE_PROJECT;

@Service
@RequiredArgsConstructor
public class ProjectService implements IProjectService {

    private final ProjectRepository repository;
    private final IUserService userService;
    private final ProjectMapper mapper;
    private final MessageSource message;

    @Override
    @Transactional
    public ProjectDto createProject(ProjectDto dto, User loggedUser) {
        Project project = mapper.dtoToProject(dto);
        project.setUser(loggedUser);
        loggedUser.getProjects().add(project);
        if(!loggedUser.getUsername().equals(project.getUser().getUsername()))
            throw new Forbidden(message.getMessage("access", null, Locale.US));
        Project savedProject = repository.save(project);
        return mapper.projectToDto(savedProject);
    }

    @Override
    @Transactional
    public ProjectDto updateProject(ProjectDto dto, Long id, User loggedUser) {
        Project project = getById(id);
        if(!loggedUser.getUsername().equals(project.getUser().getUsername()))
            throw new Forbidden(message.getMessage("access", null, Locale.US));
        Project savedProject = repository.save(mapper.updateProjectFromDto(dto, project));
        return mapper.projectToDto(project);
    }

    @Override
    public Project getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequest(
                message.getMessage("project.notFound", null, Locale.US)));
    }

    @Override
    public ProjectDto getProjectById(Long id) {
        return mapper.projectToDto(getById(id));
    }

    @Override
    public List<ProjectDto> getAllProjectsByUserId(Long id) {
        User user = userService.getById(id);
        List<Project> projects = user.getProjects();
        return mapper.projectsToDtos(projects);
    }

    @Override
    @Transactional
    public void deleteProject(Long id, User loggedUser) {
        Project project = getById(id);
        if(!loggedUser.getUsername().equals(project.getUser().getUsername()))
            throw new Forbidden(message.getMessage("access", null, Locale.US));
        repository.deleteById(id);
    }

    @Override
    public Page<Project> getProjectPage(Integer numberPage, Pageable pageable, Long userId) {
        this.getAllProjectsByUserId(userId);

        if(numberPage<0){
            throw new BadRequest(message.getMessage("page.invalid", null, Locale.US));
        }

        pageable = PageRequest.of(numberPage, 5);
        Page<Project> projectPage = repository.findAll(pageable);

        if(numberPage>=projectPage.getTotalPages()) {
            throw new NotFound(message.getMessage("page.notFound", null, Locale.US));
        }

        return projectPage;
    }

    @Override
    public Map<String, Object> responseProjectPage(Integer numberPage, Pageable pageable, Long userId) {
        Page<Project> projectPage = this.getProjectPage(numberPage, pageable, userId);
        List<Project> projects = projectPage.getContent();
        List<ProjectDto> projectDtos = mapper.projectsToDtos(projects);

        Map<String, Object> response = new HashMap<>();

        Integer lastPageNumber = projectPage.getPageable().previousOrFirst().getPageNumber();
        if(projectPage.hasPrevious()){
            response.put("lastPage", URI.create(URI_PAGE_PROJECT + lastPageNumber));
        }

        Integer nextPageNumber = projectPage.getPageable().next().getPageNumber();
        if(projectPage.hasNext()){
            response.put("nextPage", URI.create(URI_PAGE_PROJECT + nextPageNumber));
        }

        response.put("projects", projectDtos);
        response.put("currentPage", projectPage.getNumber());
        response.put("totalElements", projectPage.getTotalElements());
        response.put("totalPages", projectPage.getTotalPages());

        return response;
    }
}
