package org.missao.roxa.missaoroxabackend.modules.states.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.missao.roxa.missaoroxabackend.modules.states.application.useCase.StateUseCase;
import org.missao.roxa.missaoroxabackend.modules.states.presentation.dto.StateResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/states")
@Tag(name = "States", description = "Endpoints for managing states and municipalities")
public class StateController {

    private final StateUseCase useCase;

    public StateController(StateUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    @Operation(
            summary = "Get all states",
            description = "Retrieve all states ordered alphabetically by name"
    )
    public ResponseEntity<List<StateResponseDto>> getAll() {
        var states = useCase.find().all();
        return ResponseEntity.ok().body(states);
    }

    @GetMapping(value = "/state-name/{state-name}")
    @Operation(
            summary = "Get state by name",
            description = "Retrieve a state by its name along with its municipalities, "
                    + "ordered alphabetically by municipality name"
    )
    public ResponseEntity<StateResponseDto> getByState(@PathVariable(value = "state-name") String stateName) {
        var states = useCase.find().byState(stateName);
        return ResponseEntity.ok().body(states);
    }

}
