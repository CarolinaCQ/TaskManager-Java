package com.project.demo.service.impl;

import com.project.demo.dto.TaskGetDto;
import com.project.demo.dto.TaskPostDto;
import com.project.demo.exception.BadRequest;
import com.project.demo.exception.NotFound;
import com.project.demo.mapper.TaskMapper;
import com.project.demo.model.Condition;
import com.project.demo.model.Project;
import com.project.demo.model.Task;
import com.project.demo.repository.ConditionRepository;
import com.project.demo.repository.ProjectRepository;
import com.project.demo.repository.TaskRepository;
import com.project.demo.service.IConditionService;
import com.project.demo.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import static com.project.demo.util.Contants.Page.*;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private ConditionRepository conditionRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskMapper mapper;

    @Autowired
    private MessageSource message;

    @Override
    @Transactional
    public TaskGetDto createTask(TaskPostDto dto) {
        Task task = mapper.dtoToTask(dto);
        Integer durationInDays = durationTask(task);
        task.setDuration(durationInDays);
        addProjectToTask(task, dto.getIdProject());
        addConditionToTask(task, 1L);
        Task savedTask = repository.save(task);
        return mapper.taskToDto(savedTask);
    }

    private Integer durationTask (Task task){
        LocalDate creationDate = task.getCreationDate().toLocalDate();
        LocalDate finishDate = task.getFinishDate();
        Period period = Period.between(creationDate, finishDate);
        Integer durationInDays = period.getDays();
        return durationInDays;
    }

    @Transactional
    private void addProjectToTask(Task task, Long idProject){
        Project project = projectRepository.findById(idProject).get();
        task.setProject(project);

        project.getTasks().add(task);
    }

    @Transactional
    private void addConditionToTask(Task task, Long id) {
        Condition condition = conditionRepository.findById(id).get();
        task.setCondition(condition);

        condition.getTasks().add(task);
    }

    @Override
    @Transactional
    public TaskGetDto updateTask(TaskPostDto dto, Long id) {
        if(!findById(id).isPresent()) throw new BadRequest(
                message.getMessage("task.notFound", null, Locale.US));
        Task task = repository.save(mapper.updateTaskFromDto(dto, findById(id).get()));
        Integer durationInDays = this.durationTask(task);
        task.setDuration(durationInDays);
        return mapper.taskToDto(task);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public TaskGetDto getById(Long id) {
        if(!findById(id).isPresent()) throw new BadRequest(
                message.getMessage("task.notFound", null, Locale.US));
        Task task = repository.findById(id).get();
        return mapper.taskToDto(task);
    }

    @Override
    public List<TaskGetDto> getAllTasksByProjectId(Long projectId) {
        Project project = projectRepository.findById(projectId).get();
        if(Objects.isNull(project)) throw new BadRequest(
                message.getMessage("project.notFound", null, Locale.US));
        List<Task> tasks = project.getTasks();
        return mapper.tasksToDtos(tasks);
    }

    @Override
    public List<TaskGetDto> getAllTasksByConditionId(Long conditionId) {
        Condition condition = conditionRepository.findById(conditionId).get();
        if(Objects.isNull(condition)) throw new BadRequest(
                message.getMessage("project.notFound", null, Locale.US));
        List<Task> tasks = condition.getTasks();
        return mapper.tasksToDtos(tasks);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        if(!findById(id).isPresent()) throw new BadRequest(
                message.getMessage("task.notFound", null, Locale.US));
        repository.deleteById(id);
    }

    @Override
    public Page<Task> getTaskPage(Integer numberPage, Pageable pageable, Long projectId) {
        this.getAllTasksByProjectId(projectId);

        if(numberPage<0){
            throw new BadRequest(message.getMessage("page.invalid", null, Locale.US));
        }

        pageable = PageRequest.of(numberPage, 5);
        Page<Task> taskPage = repository.findAll(pageable);

        if(numberPage>=taskPage.getTotalPages()) {
            throw new NotFound(message.getMessage("page.notFound", null, Locale.US));
        }

        return taskPage;
    }

    @Override
    public Map<String, Object> responseTaskPage(Integer numberPage, Pageable pageable, Long projectId) {

        Page<Task> taskPage = this.getTaskPage(numberPage, pageable, projectId);
        List<Task> tasks = taskPage.getContent();
        List<TaskGetDto> tasksDtos = mapper.tasksToDtos(tasks);

        Map<String, Object> response = new HashMap<>();

        Integer lastPageNumber = taskPage.getPageable().previousOrFirst().getPageNumber();
        if(taskPage.hasPrevious()){
            response.put("lastPage", URI.create(URI_PAGE_TASK_1 + lastPageNumber + URI_PAGE_TASK_2 + projectId));
        }

        Integer nextPageNumber = taskPage.getPageable().next().getPageNumber();
        if(taskPage.hasNext()){
            response.put("nextPage", URI.create(URI_PAGE_TASK_1 + nextPageNumber + URI_PAGE_TASK_2 + projectId));
        }

        response.put("projects", tasksDtos);
        response.put("currentPage", taskPage.getNumber());
        response.put("totalElements", taskPage.getTotalElements());
        response.put("totalPages", taskPage.getTotalPages());

        return response;
    }

    @Override
    public Page<Task> getTaskPageByCondition(Integer numberPage, Pageable pageable, Long conditionId) {
        this.getAllTasksByConditionId(conditionId);

        if(numberPage<0){
            throw new BadRequest(message.getMessage("page.invalid", null, Locale.US));
        }

        pageable = PageRequest.of(numberPage, 5);
        Page<Task> taskPage = repository.findAll(pageable);

        if(numberPage>=taskPage.getTotalPages()) {
            throw new NotFound(message.getMessage("page.notFound", null, Locale.US));
        }

        return taskPage;
    }

    @Override
    public Map<String, Object> responseTaskPageByCondition(Integer numberPage, Pageable pageable, Long conditionId) {
        Page<Task> taskPage = this.getTaskPage(numberPage, pageable, conditionId);
        List<Task> tasks = taskPage.getContent();
        List<TaskGetDto> tasksDtos = mapper.tasksToDtos(tasks);

        Map<String, Object> response = new HashMap<>();

        Integer lastPageNumber = taskPage.getPageable().previousOrFirst().getPageNumber();
        if(taskPage.hasPrevious()){
            response.put("lastPage", URI.create(URI_PAGE_TASK_3 + lastPageNumber + URI_PAGE_TASK_2 + conditionId));
        }

        Integer nextPageNumber = taskPage.getPageable().next().getPageNumber();
        if(taskPage.hasNext()){
            response.put("nextPage", URI.create(URI_PAGE_TASK_3 + nextPageNumber + URI_PAGE_TASK_2 + conditionId));
        }

        response.put("projects", tasksDtos);
        response.put("currentPage", taskPage.getNumber());
        response.put("totalElements", taskPage.getTotalElements());
        response.put("totalPages", taskPage.getTotalPages());

        return response;
    }
}
