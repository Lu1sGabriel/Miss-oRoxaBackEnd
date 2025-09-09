package org.missao.roxa.missaoroxabackend.modules.states.application.useCase.find;

import org.missao.roxa.missaoroxabackend.modules.states.presentation.dto.StateResponseDto;

import java.util.List;

public interface IStateFind {
    List<StateResponseDto> all();

    StateResponseDto byState(String stateName);

}