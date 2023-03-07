package com.project.demo.service.impl;

import com.project.demo.dto.ConditionDto;
import com.project.demo.dto.TaskGetDto;
import com.project.demo.exception.BadRequest;
import com.project.demo.mapper.ConditionMapper;
import com.project.demo.mapper.TaskMapper;
import com.project.demo.model.Condition;
import com.project.demo.model.Task;
import com.project.demo.repository.ConditionRepository;
import com.project.demo.service.IConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ConditionService implements IConditionService {

    @Autowired
    private ConditionRepository repository;

    @Autowired
    private ConditionMapper mapper;

    @Autowired
    private MessageSource message;


    @Override
    @Transactional
    public ConditionDto createCondition(ConditionDto dto) {
        Condition condition = repository.save(mapper.dtoToCondition(dto));
        return mapper.conditionToDto(condition);
    }

    @Override
    public ConditionDto updateCondition(ConditionDto dto, Long id) {
        Condition condition = getById(id);
        Condition savedCondition = repository.save(mapper.updateConditionFromDto(dto, condition));
        return mapper.conditionToDto(savedCondition);
    }

    @Override
    public Condition getById (Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequest(
                message.getMessage("condition.notFound", null, Locale.US)));
    }

    @Override
    public ConditionDto getConditionById(Long id) {
        return mapper.conditionToDto(getById(id));
    }

    @Override
    public List<ConditionDto> getAllConditions() {
        List<Condition> conditions = repository.findAll();
        return mapper.conditionsToDtos(conditions);
    }

    @Override
    public void deleteCondition(Long id) {
        Condition condition = getById(id);
        repository.deleteById(id);
    }
}
