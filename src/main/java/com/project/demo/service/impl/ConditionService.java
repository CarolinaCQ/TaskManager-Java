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
        condition.setDeleted(false);
        return mapper.conditionToDto(condition);
    }

    @Override
    public ConditionDto updateCondition(ConditionDto dto, Long id) {
        if(!findById(id).isPresent()) throw new BadRequest(
                message.getMessage("condition.notFound", null, Locale.US));
        Condition condition = repository.save(mapper.updateConditionFromDto(dto, findById(id).get()));
        return mapper.conditionToDto(condition);
    }

    @Override
    public Optional<Condition> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ConditionDto getById(Long id) {
        if(!findById(id).isPresent()) throw new BadRequest(
                message.getMessage("condition.notFound", null, Locale.US));
        Condition condition = findById(id).get();
        return mapper.conditionToDto(condition);
    }

    @Override
    public List<ConditionDto> getAllConditions() {
        List<Condition> conditions = repository.findAll();
        return mapper.conditionsToDtos(conditions);
    }

    @Override
    public void deleteCondition(Long id) {
        if(!findById(id).isPresent()) throw new BadRequest(
                message.getMessage("condition.notFound", null, Locale.US));
        repository.deleteById(id);
    }
}
