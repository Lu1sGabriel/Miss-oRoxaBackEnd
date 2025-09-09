package org.missao.roxa.missaoroxabackend.modules.states.application.useCase.find;

import org.missao.roxa.missaoroxabackend.core.exception.HttpException;
import org.missao.roxa.missaoroxabackend.modules.states.infrastructure.repository.StateRepository;
import org.missao.roxa.missaoroxabackend.modules.states.presentation.dto.StateResponseDto;
import org.missao.roxa.missaoroxabackend.modules.states.shared.mapper.StateMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateFind implements IStateFind {
    private final StateRepository stateRepository;
    private final StateMapper mapper;

    public StateFind(StateRepository stateRepository, StateMapper mapper) {
        this.stateRepository = stateRepository;
        this.mapper = mapper;
    }

    @Override
    @Cacheable(value = "allStates")
    public List<StateResponseDto> all() {
        return mapper.toDtoList(stateRepository.findAllByOrderByNameAsc());
    }

    @Override
    @Cacheable(value = "stateWithRelatedEntities")
    public StateResponseDto byState(String stateName) {
        return stateRepository.findByName_NameIgnoreCase(stateName)
                .map(mapper::toDtoWithRelatedEntities)
                .orElseThrow(() -> HttpException.notFound("State not found with provided state name."));
    }

}