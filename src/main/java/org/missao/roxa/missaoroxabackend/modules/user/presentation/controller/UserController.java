package org.missao.roxa.missaoroxabackend.modules.user.presentation.controller;

import org.missao.roxa.missaoroxabackend.modules.user.application.useCase.UserUseCase;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserCreateDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserUseCase useCase;

    public UserController(UserUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateDto dto) {
        var user = useCase.getCreate().create(dto);
        return ResponseEntity.ok().body(user);
    }

}