package com.project.demo.service.impl;

import com.project.demo.dto.SubtaskGetDto;
import com.project.demo.dto.SubtaskPostDto;
import com.project.demo.dto.TaskGetDto;
import com.project.demo.exception.BadRequest;
import com.project.demo.exception.NotFound;
import com.project.demo.mapper.SubtaskMapper;
import com.project.demo.model.Condition;
import com.project.demo.model.Subtask;
import com.project.demo.model.Task;
import com.project.demo.repository.SubtaskRepository;
import com.project.demo.repository.TaskRepository;
import com.project.demo.service.ISubtaskService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SubtaskService implements ISubtaskService {

    @Autowired
    private SubtaskRepository repository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubtaskMapper mapper;

    @Autowired
    private MessageSource message;

    @Override
    @Transactional
    public SubtaskGetDto createSubtask(SubtaskPostDto dto) {
        Subtask subtask = mapper.dtoToSubtask(dto);
        addTaskToSubtasks(subtask, dto.getIdTask());
        Subtask savedSubtask = repository.save(subtask);
        return mapper.subtaskToDto(savedSubtask);
    }

    @Transactional
    private void addTaskToSubtasks(Subtask subtask, Long idTask){
        Task task = taskRepository.findById(idTask).get();
        subtask.setTask(task);

        task.getSubtasks().add(subtask);
    }

    @Override
    @Transactional
    public SubtaskGetDto updateSubtask(SubtaskPostDto dto, Long id) {
        if(!findById(id).isPresent()) throw new BadRequest(
                message.getMessage("subtask.notFound", null, Locale.US));
        Subtask subtask = repository.save(mapper.updateSubtaskFromDto(dto, findById(id).get()));
        return mapper.subtaskToDto(subtask);
    }

    @Override
    public Optional<Subtask> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public SubtaskGetDto getById(Long id) {
        if(!findById(id).isPresent()) throw new BadRequest(
                message.getMessage("subtask.notFound", null, Locale.US));
        Subtask subtask = findById(id).get();
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
    public void deleteSubtask(Long id) {
        if(!findById(id).isPresent()) throw new BadRequest(
                message.getMessage("subtask.notFound", null, Locale.US));
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
