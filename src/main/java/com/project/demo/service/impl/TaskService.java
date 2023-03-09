package com.project.demo.service.impl;

import com.project.demo.dto.TaskGetDto;
import com.project.demo.dto.TaskPostDto;
import com.project.demo.exception.BadRequest;
import com.project.demo.exception.Forbidden;
import com.project.demo.exception.NotFound;
import com.project.demo.mapper.TaskMapper;
import com.project.demo.model.Condition;
import com.project.demo.model.Project;
import com.project.demo.model.Task;
import com.project.demo.model.User;
import com.project.demo.repository.TaskRepository;
import com.project.demo.service.IConditionService;
import com.project.demo.service.IProjectService;
import com.project.demo.service.ITaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import static com.project.demo.util.Contants.Page.*;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {

    private final TaskRepository repository;
    private final IConditionService conditionService;
    private final IProjectService projectService;
    private final TaskMapper mapper;
    private final MessageSource message;

    @Override
    @Transactional
    public TaskGetDto createTask(TaskPostDto dto, User loggedUser) {

        Task task = mapper.dtoToTask(dto);
        task.setDuration(durationTask(task));

        Project project= projectService.getById(dto.getIdProject());
        task.setProject(project);

        Condition condition = conditionService.getById(1L);
        task.setCondition(condition);


        if(!loggedUser.getUsername().equals(project.getUser().getUsername()))
            throw new Forbidden(message.getMessage("access", null, Locale.US));

        Task savedTask = repository.save(task);

        project.getTasks().add(savedTask);
        condition.getTasks().add(savedTask);

        return mapper.taskToDto(savedTask);
    }

    private Integer durationTask (Task task){
        LocalDate creationDate = task.getCreationDate().toLocalDate();
        LocalDate finishDate = task.getFinishDate();
        Period period = Period.between(creationDate, finishDate);
        Integer durationInDays = period.getDays();
        return durationInDays;
    }

    @Override
    @Transactional
    public TaskGetDto updateTask(TaskPostDto dto, Long id, User loggedUser) {
        Task task = getById(id);
        if(!loggedUser.getUsername().equals(task.getProject().getUser().getUsername()))
            throw new Forbidden(message.getMessage("access", null, Locale.US));
        Task savedTask = repository.save(mapper.updateTaskFromDto(dto, task));
        savedTask.setDuration(durationTask(task));
        return mapper.taskToDto(savedTask);
    }

    @Override
    public Task getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequest(
                message.getMessage("task.notFound", null, Locale.US)));
    }

    @Override
    public TaskGetDto getTaskById(Long id) {
        return mapper.taskToDto(getById(id));
    }

    @Override
    public List<TaskGetDto> getAllTasksByProjectId(Long projectId) {
        Project project = projectService.getById(projectId);
        List<Task> tasks = project.getTasks();
        return mapper.tasksToDtos(tasks);
    }

    @Override
    public List<TaskGetDto> getAllTasksByConditionId(Long conditionId) {
        Condition condition = conditionService.getById(conditionId);
        List<Task> tasks = condition.getTasks();
        return mapper.tasksToDtos(tasks);
    }

    @Override
    @Transactional
    public void deleteTask(Long id, User loggedUser) {
        Task task = getById(id);
        if(!loggedUser.getUsername().equals(task.getProject().getUser().getUsername()))
            throw new Forbidden(message.getMessage("access", null, Locale.US));
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
