package com.project.demo.mapper;

import com.project.demo.dto.TaskGetDto;
import com.project.demo.dto.TaskPostDto;
import com.project.demo.model.Task;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task dtoToTask (TaskPostDto dto);

    TaskGetDto taskToDto (Task task);

    Task dtoToTask (TaskGetDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task updateTaskFromDto(TaskPostDto dto, @MappingTarget Task task);

    List<TaskGetDto> tasksToDtos (List<Task> tasks);
}
