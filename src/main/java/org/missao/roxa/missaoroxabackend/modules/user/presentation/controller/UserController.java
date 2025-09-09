package org.missao.roxa.missaoroxabackend.modules.user.presentation.controller;

import org.missao.roxa.missaoroxabackend.modules.user.application.useCase.UserUseCase;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserChangeBirthDateDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserChangeFullNameDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserCreateDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        var user = useCase.find().byId(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAll(@RequestParam(value = "order", required = false, defaultValue = "asc") String order,
                                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                        @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        var users = useCase.find().allByOrderById(order, pageable);
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/name/{full-name}")
    public ResponseEntity<UserResponseDto> getByFullName(@PathVariable(value = "full-name") String fullName) {
        var user = useCase.find().byFullName(fullName);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/birthdays")
    public ResponseEntity<List<UserResponseDto>> getAllBirthday(@RequestParam(value = "month", required = false) Integer month,
                                                                @RequestParam(value = "day", required = false) Integer day) {
        var users = useCase.find().getBirthdays(month, day);
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
        var user = useCase.changeFullName().change(id, dto);
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping(value = "/change-birthdate/{id}")
    public ResponseEntity<UserResponseDto> changeBirthDate(@PathVariable(value = "id") UUID id,
                                                           @RequestBody UserChangeBirthDateDto dto) {
        var user = useCase.changeBirthDate().change(id, dto);
        return ResponseEntity.ok().body(user);
    }

}