package com.project.demo.controller;

import com.project.demo.documentation.IProjectController;
import com.project.demo.dto.ProjectDto;
import com.project.demo.model.User;
import com.project.demo.service.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("projects")
public class ProjectController implements IProjectController {

    private final IProjectService service;

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id){
        ProjectDto project = service.getProjectById(id);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @GetMapping("/page")
    @Override
    public ResponseEntity<Map<String,Object>> pageProjects(@RequestParam Integer numberPage, @RequestParam Long id, Pageable pageable, @AuthenticationPrincipal User loggedUser){
        Map<String,Object> projects = service.responseProjectPage(numberPage,pageable,id,loggedUser);
        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    @PostMapping
    @Override
    public ResponseEntity<ProjectDto> createProject(@RequestBody @Valid ProjectDto dto, @AuthenticationPrincipal User loggedUser){
        ProjectDto project = service.createProject(dto, loggedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @PostMapping("/add-collaborator/{id}")
    @Override
    public ResponseEntity<Void> addCollaborators(@PathVariable Long id, @RequestParam String username) {
        service.addCollaborators(id,username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<ProjectDto> updateProject(@RequestBody @Valid ProjectDto dto, @PathVariable Long id, @AuthenticationPrincipal User loggedUser){
        ProjectDto project = service.updateProject(dto, id, loggedUser);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteProject(@PathVariable Long id, @AuthenticationPrincipal User loggedUser){
        service.deleteProject(id, loggedUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
