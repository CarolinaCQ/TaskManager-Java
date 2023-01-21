package com.project.demo.controller;

import com.project.demo.dto.TaskGetDto;
import com.project.demo.dto.TaskPostDto;
import com.project.demo.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("tasks")
public class TaskController {

    @Autowired
    private ITaskService service;

    @GetMapping("/{id}")
    public ResponseEntity<TaskGetDto> getTaskById(@PathVariable Long id){
        TaskGetDto task = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/page")
    public ResponseEntity<Map<String,Object>> pageTasks(@RequestParam Integer numberPage, @RequestParam Long id, Pageable pageable){
        Map<String,Object> tasks = service.responseTaskPage(numberPage,pageable,id);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @GetMapping("/page/condition")
    public ResponseEntity<Map<String,Object>> pageTasksByCondition(@RequestParam Integer numberPage, @RequestParam Long id, Pageable pageable){
        Map<String,Object> tasks = service.responseTaskPageByCondition(numberPage,pageable,id);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @PostMapping
    public ResponseEntity<TaskGetDto> createTask(@RequestBody @Valid TaskPostDto dto){
        TaskGetDto task = service.createTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskGetDto> updateTask(@RequestBody @Valid TaskPostDto dto, @PathVariable Long id){
        TaskGetDto task = service.updateTask(dto, id);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        service.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
