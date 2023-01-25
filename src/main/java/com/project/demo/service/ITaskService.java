package com.project.demo.service;

import com.project.demo.dto.TaskGetDto;
import com.project.demo.dto.TaskPostDto;
import com.project.demo.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ITaskService {

    TaskGetDto createTask(TaskPostDto dto);

    TaskGetDto updateTask(TaskPostDto dto, Long id);
    Optional<Task> findById(Long id);
    TaskGetDto getById(Long id);
    List<TaskGetDto> getAllTasksByProjectId(Long projectId);
    List<TaskGetDto> getAllTasksByConditionId(Long conditionId);
    void deleteTask(Long id);
    Page<Task> getTaskPage(Integer numberPage, Pageable pageable, Long projectId);
    Map<String, Object> responseTaskPage(Integer numberPage, Pageable pageable, Long projectId);
    Page<Task> getTaskPageByCondition(Integer numberPage, Pageable pageable, Long conditionId);
    Map<String, Object> responseTaskPageByCondition(Integer numberPage, Pageable pageable, Long conditionId);

}
