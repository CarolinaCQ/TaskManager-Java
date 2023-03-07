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
@RequestMapping("projects")
public class ProjectController implements IProjectController {

    @Autowired
    private IProjectService service;

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id){
        ProjectDto project = service.getProjectById(id);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @GetMapping("/page")
    public ResponseEntity<Map<String,Object>> pageProjects(@RequestParam Integer numberPage, @RequestParam Long id, Pageable pageable){
        Map<String,Object> projects = service.responseProjectPage(numberPage,pageable,id);
        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody @Valid ProjectDto dto, @AuthenticationPrincipal User loggedUser){
        ProjectDto project = service.createProject(dto, loggedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(@RequestBody @Valid ProjectDto dto, @PathVariable Long id, @AuthenticationPrincipal User loggedUser){
        ProjectDto project = service.updateProject(dto, id, loggedUser);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id, @AuthenticationPrincipal User loggedUser){
        service.deleteProject(id, loggedUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
