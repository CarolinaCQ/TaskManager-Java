package com.project.demo.service;

import com.project.demo.dto.TaskGetDto;
import com.project.demo.dto.TaskPostDto;
import com.project.demo.model.Task;
import com.project.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ITaskService {

    TaskGetDto createTask(TaskPostDto dto, User loggedUser);

    TaskGetDto updateTask(TaskPostDto dto, Long id, User loggedUser);
    Task getById(Long id);
    TaskGetDto getTaskById(Long id);
    List<TaskGetDto> getAllTasksByProjectId(Long projectId);
    List<TaskGetDto> getAllTasksByConditionId(Long conditionId);
    void deleteTask(Long id, User loggedUser);
    void validAccess(Task task, User loggedUser);
    Page<Task> getTaskPage(Integer numberPage, Pageable pageable, Long projectId);
    Map<String, Object> responseTaskPage(Integer numberPage, Pageable pageable, Long projectId);
    Page<Task> getTaskPageByCondition(Integer numberPage, Pageable pageable, Long conditionId);
    Map<String, Object> responseTaskPageByCondition(Integer numberPage, Pageable pageable, Long conditionId);

}
