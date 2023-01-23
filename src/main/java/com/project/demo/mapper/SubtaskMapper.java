package com.project.demo.mapper;

import com.project.demo.dto.SubtaskGetDto;
import com.project.demo.dto.SubtaskPostDto;
import com.project.demo.model.Subtask;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubtaskMapper {

    Subtask dtoToSubtask (SubtaskPostDto dto);

    SubtaskGetDto subtaskToDto (Subtask subtask);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Subtask updateSubtaskFromDto (SubtaskPostDto dto, @MappingTarget Subtask subtask);

    List<SubtaskGetDto> subtasksToDtos (List<Subtask> subtasks);
}
