package com.project.demo.service.impl;

import com.project.demo.dto.SubtaskGetDto;
import com.project.demo.dto.SubtaskPostDto;
import com.project.demo.exception.BadRequest;
import com.project.demo.exception.Forbidden;
import com.project.demo.exception.NotFound;
import com.project.demo.mapper.SubtaskMapper;
import com.project.demo.model.Subtask;
import com.project.demo.model.User;
import com.project.demo.repository.SubtaskRepository;
import com.project.demo.repository.TaskRepository;
import com.project.demo.service.ISubtaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.*;

import static com.project.demo.util.Contants.Page.*;

@Service
@RequiredArgsConstructor
public class SubtaskService implements ISubtaskService {

    private final SubtaskRepository repository;
    private final TaskRepository taskRepository;
    private final SubtaskMapper mapper;
    private final MessageSource message;

    @Override
    @Transactional
    public SubtaskGetDto createSubtask(SubtaskPostDto dto, User loggedUser) {
        Subtask subtask = mapper.dtoToSubtask(dto);
        subtask.setTask(taskRepository.findById(dto.getIdTask()).get());
        subtask.getTask().getSubtasks().add(subtask);
        if(!loggedUser.getUsername().equals(subtask.getTask().getProject().getUser().getUsername()))
            throw new Forbidden(message.getMessage("access",null,Locale.US));
        Subtask savedSubtask = repository.save(subtask);
        return mapper.subtaskToDto(savedSubtask);
    }

    @Override
    @Transactional
    public SubtaskGetDto updateSubtask(SubtaskPostDto dto, Long id, User loggedUser) {
        Subtask subtask = getById(id);
        if(!loggedUser.getUsername().equals(subtask.getTask().getProject().getUser().getUsername()))
            throw new Forbidden(message.getMessage("access",null,Locale.US));
        Subtask savedSubtask = repository.save(mapper.updateSubtaskFromDto(dto, subtask));
        return mapper.subtaskToDto(savedSubtask);
    }

    @Override
    public Subtask getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequest(
                message.getMessage("subtask.notFound", null, Locale.US)));
    }

    @Override
    public SubtaskGetDto getSubtaskById(Long id) {
        Subtask subtask = getById(id);
        return mapper.subtaskToDto(subtask);
    }

    @Override
    public List<SubtaskGetDto> getAllSubtasksByTaskId(Long taskId) {
        if(!taskRepository.findById(taskId).isPresent()) throw new BadRequest(
                message.getMessage("task.notFound", null, Locale.US));
        List<Subtask> subtasks = taskRepository.findById(taskId).get().getSubtasks();
        return mapper.subtasksToDtos(subtasks);
    }

    @Override
    @Transactional
    public void deleteSubtask(Long id, User loggedUser) {
        Subtask subtask = getById(id);
        if(!loggedUser.getUsername().equals(subtask.getTask().getProject().getUser().getUsername()))
            throw new Forbidden(message.getMessage("access",null,Locale.US));
        repository.deleteById(id);
    }

    @Override
    public Page<Subtask> getSubtaskPage(Integer numberPage, Pageable pageable, Long taskId) {
        this.getAllSubtasksByTaskId(taskId);

        if(numberPage<0){
            throw new BadRequest(message.getMessage("page.invalid", null, Locale.US));
        }

        pageable = PageRequest.of(numberPage, 5);
        Page<Subtask> subtaskPage = repository.findAll(pageable);

        if(numberPage>=subtaskPage.getTotalPages()) {
            throw new NotFound(message.getMessage("page.notFound", null, Locale.US));
        }

        return subtaskPage;
    }

    @Override
    public Map<String, Object> responseSubtaskPage(Integer numberPage, Pageable pageable, Long taskId) {
        Page<Subtask> subtaskPage = this.getSubtaskPage(numberPage, pageable, taskId);
        List<Subtask> subtasks = subtaskPage.getContent();
        List<SubtaskGetDto> subtasksDtos = mapper.subtasksToDtos(subtasks);

        Map<String, Object> response = new HashMap<>();

        Integer lastPageNumber = subtaskPage.getPageable().previousOrFirst().getPageNumber();
        if(subtaskPage.hasPrevious()){
            response.put("lastPage", URI.create(URI_PAGE_SUBTASK + lastPageNumber + URI_PAGE_TASK_2 + taskId));
        }

        Integer nextPageNumber = subtaskPage.getPageable().next().getPageNumber();
        if(subtaskPage.hasNext()){
            response.put("nextPage", URI.create(URI_PAGE_SUBTASK + nextPageNumber + URI_PAGE_TASK_2 + taskId));
        }

        response.put("subtasks", subtasksDtos);
        response.put("currentPage", subtaskPage.getNumber());
        response.put("totalElements", subtaskPage.getTotalElements());
        response.put("totalPages", subtaskPage.getTotalPages());

        return response;
    }
}
