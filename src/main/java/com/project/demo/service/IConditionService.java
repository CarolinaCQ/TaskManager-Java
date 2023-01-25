package com.project.demo.service;

import com.project.demo.dto.ConditionDto;
import com.project.demo.dto.TaskGetDto;
import com.project.demo.model.Condition;
import com.project.demo.model.Task;

import java.util.List;
import java.util.Optional;

public interface IConditionService {

    ConditionDto createCondition (ConditionDto dto);
    ConditionDto updateCondition (ConditionDto dto, Long id);
    Optional<Condition> findById (Long id);
    ConditionDto getById (Long id);
    List<ConditionDto> getAllConditions ();
    void deleteCondition (Long id);
}
