package com.project.demo.controller;

import com.project.demo.documentation.ITaskController;
import com.project.demo.dto.TaskGetDto;
import com.project.demo.dto.TaskPostDto;
import com.project.demo.model.User;
import com.project.demo.service.ITaskService;
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
@RequestMapping("tasks")
@RequiredArgsConstructor
public class TaskController implements ITaskController {

    private final ITaskService service;

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<TaskGetDto> getTaskById(@PathVariable Long id){
        TaskGetDto task = service.getTaskById(id);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/page")
    @Override
    public ResponseEntity<Map<String,Object>> pageTasks(@RequestParam Integer numberPage, @RequestParam Long id, Pageable pageable){
        Map<String,Object> tasks = service.responseTaskPage(numberPage,pageable,id);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @GetMapping("/page/condition")
    @Override
    public ResponseEntity<Map<String,Object>> pageTasksByCondition(@RequestParam Integer numberPage, @RequestParam Long id, Pageable pageable){
        Map<String,Object> tasks = service.responseTaskPageByCondition(numberPage,pageable,id);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @PostMapping
    @Override
    public ResponseEntity<TaskGetDto> createTask(@RequestBody @Valid TaskPostDto dto, @AuthenticationPrincipal User loggedUser){
        TaskGetDto task = service.createTask(dto, loggedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<TaskGetDto> updateTask(@RequestBody @Valid TaskPostDto dto, @PathVariable Long id, @AuthenticationPrincipal User loggedUser){
        TaskGetDto task = service.updateTask(dto, id, loggedUser);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, @AuthenticationPrincipal User loggedUser){
        service.deleteTask(id, loggedUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
