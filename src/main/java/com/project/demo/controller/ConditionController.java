package com.project.demo.controller;

import com.project.demo.documentation.IConditionController;
import com.project.demo.dto.ConditionDto;
import com.project.demo.service.IConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("tasks/conditions")
public class ConditionController implements IConditionController {

    @Autowired
    private IConditionService service;

    @GetMapping("/{id}")
    public ResponseEntity<ConditionDto> geConditionyId(@PathVariable Long id){
        ConditionDto condition = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(condition);
    }

    @GetMapping
    public ResponseEntity<List<ConditionDto>> getAllConditions(){
        List<ConditionDto> conditions = service.getAllConditions();
        return ResponseEntity.status(HttpStatus.OK).body(conditions);
    }

    @PostMapping
    public ResponseEntity<ConditionDto> createCondition(@RequestBody @Valid ConditionDto dto){
        ConditionDto condition = service.createCondition(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(condition);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ConditionDto> updateCondition(@RequestBody @Valid ConditionDto dto, @PathVariable Long id){
        ConditionDto condition = service.updateCondition(dto, id);
        return ResponseEntity.status(HttpStatus.OK).body(condition);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCondition(@PathVariable Long id){
        service.deleteCondition(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
