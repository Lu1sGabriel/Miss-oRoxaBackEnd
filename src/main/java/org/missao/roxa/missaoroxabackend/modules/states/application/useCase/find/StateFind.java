package org.missao.roxa.missaoroxabackend.modules.states.application.useCase.find;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.shared.utils.Validator;
import org.missao.roxa.missaoroxabackend.modules.states.infrastructure.repository.StateRepository;
import org.missao.roxa.missaoroxabackend.modules.states.presentation.dto.StateResponseDto;
import org.missao.roxa.missaoroxabackend.modules.states.shared.mapper.StateMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateFind implements IStateFind {
    private final StateRepository stateRepository;
    private final StateMapper mapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "allStates")
    public List<StateResponseDto> all() {
        return mapper.toDtoList(stateRepository.findAllByOrderByNameAsc());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "stateWithRelatedEntities")
    public StateResponseDto byState(String stateName) {
        return stateRepository.findByName(Validator.requireNonEmpty(stateName))
                .map(mapper::toDtoWithRelatedEntities)
                .orElseThrow(() -> new EntityNotFoundException("State not found with provided state name."));
    }

}