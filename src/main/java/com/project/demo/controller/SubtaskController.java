package com.project.demo.controller;

import com.project.demo.documentation.ISubtaskController;
import com.project.demo.dto.SubtaskGetDto;
import com.project.demo.dto.SubtaskPostDto;
import com.project.demo.model.User;
import com.project.demo.service.ISubtaskService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("subtasks")
@RequiredArgsConstructor
public class SubtaskController implements ISubtaskController {

    private final ISubtaskService service;

    @GetMapping("/{id}")
    public ResponseEntity<SubtaskGetDto> getSubtaskById(@PathVariable Long id){
        SubtaskGetDto subtask = service.getSubtaskById(id);
        return ResponseEntity.status(HttpStatus.OK).body(subtask);
    }

    @GetMapping("/page")
    public ResponseEntity<Map<String,Object>> pageSubtasks(@RequestParam Integer numberPage, @RequestParam Long id, Pageable pageable){
        Map<String,Object> subtasks = service.responseSubtaskPage(numberPage,pageable,id);
        return ResponseEntity.status(HttpStatus.OK).body(subtasks);
    }

    @PostMapping
    public ResponseEntity<SubtaskGetDto> createSubtask(@RequestBody @Valid SubtaskPostDto dto, @AuthenticationPrincipal User loggedUser){
        SubtaskGetDto subtask = service.createSubtask(dto, loggedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(subtask);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SubtaskGetDto> updateSubtask(@RequestBody @Valid SubtaskPostDto dto, @PathVariable Long id, @AuthenticationPrincipal User loggedUser){
        SubtaskGetDto subtask = service.updateSubtask(dto, id, loggedUser);
        return ResponseEntity.status(HttpStatus.OK).body(subtask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubtask(@PathVariable Long id, @AuthenticationPrincipal User loggedUser){
        service.deleteSubtask(id, loggedUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
