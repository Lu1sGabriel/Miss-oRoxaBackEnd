package org.missao.roxa.missaoroxabackend.modules.user.presentation.controller;

import org.missao.roxa.missaoroxabackend.modules.user.application.useCase.UserUseCase;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserChangeBirthDateDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserChangeFullNameDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserCreateDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserUseCase useCase;

    public UserController(UserUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable(value = "id") UUID id) {
        var user = useCase.getFind().byId(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                        @RequestParam(value = "page-size", required = false, defaultValue = "20") int pageSize,
                                                        @RequestParam(value = "sort-direction", required = false, defaultValue = "ASC") String sortDirection) {
        var users = useCase.getFind().all(page, pageSize, sortDirection);
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/name/{full-name}")
    public ResponseEntity<UserResponseDto> getByFullName(@PathVariable(value = "full-name") String fullName) {
        var user = useCase.getFind().byFullName(fullName);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/birthdays")
    public ResponseEntity<List<UserResponseDto>> getAllBirthday(@RequestParam(value = "month", required = false) int month,
                                                                @RequestParam(value = "day", required = false) int day) {
        var users = useCase.getFind().byBirthdays(month, day);
        return ResponseEntity.ok().body(users);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateDto dto) {
        var user = useCase.getCreate().create(dto);
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping(value = "/change-full-name/{id}")
    public ResponseEntity<UserResponseDto> changeFullName(@PathVariable(value = "id") UUID id,
                                                          @RequestBody UserChangeFullNameDto dto) {
        var user = useCase.getChangeFullName().change(id, dto);
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping(value = "/change-birthdate/{id}")
    public ResponseEntity<UserResponseDto> changeBirthDate(@PathVariable(value = "id") UUID id,
                                                           @RequestBody UserChangeBirthDateDto dto) {
        var user = useCase.getChangeBirthDate().change(id, dto);
        return ResponseEntity.ok().body(user);
    }

}