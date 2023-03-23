package com.project.demo.service;

import com.project.demo.dto.SubtaskGetDto;
import com.project.demo.dto.SubtaskPostDto;
import com.project.demo.dto.TaskGetDto;
import com.project.demo.dto.TaskPostDto;
import com.project.demo.model.Subtask;
import com.project.demo.model.Task;
import com.project.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ISubtaskService {

    SubtaskGetDto createSubtask(SubtaskPostDto dto, User loggedUser);
    SubtaskGetDto updateSubtask(SubtaskPostDto dto, Long id, User loggedUser);
    Subtask getById(Long id);
    SubtaskGetDto getSubtaskById(Long id);
    List<SubtaskGetDto> getAllSubtasksByTaskId(Long taskId);
    void deleteSubtask(Long id, User loggedUser);
    void validAccess(Subtask subtask, User loggedUser);
    Page<Subtask> getSubtaskPage(Integer numberPage, Pageable pageable, Long taskId);
    Map<String, Object> responseSubtaskPage(Integer numberPage, Pageable pageable, Long taskId);

}
