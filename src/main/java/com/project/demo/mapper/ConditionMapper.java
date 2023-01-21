package com.project.demo.mapper;

import com.project.demo.dto.ConditionDto;
import com.project.demo.model.Condition;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConditionMapper {

    Condition dtoToCondition(ConditionDto dto);

    ConditionDto conditionToDto (Condition condition);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Condition updateConditionFromDto(ConditionDto dto, @MappingTarget Condition condition);

    List<ConditionDto> conditionsToDtos (List<Condition> conditions);
}
