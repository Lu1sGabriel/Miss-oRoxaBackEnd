package org.missao.roxa.missaoroxabackend.modules.category.presentation.controller;

import org.missao.roxa.missaoroxabackend.modules.category.application.useCase.CategoryUseCase;
import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model.CategoryUpdateDto;
import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model.CategoryCreateDto;
import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model.CategoryResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController {

    private final CategoryUseCase useCase;

    public CategoryController(CategoryUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> findAll() {
        var categories = useCase.getFind().all();
        return ResponseEntity.ok().body(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(@RequestBody CategoryCreateDto dto) {
        var category = useCase.getCreate().create(dto);
        return ResponseEntity.ok().body(category);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable(value = "id") UUID id,
                                                          @RequestBody CategoryUpdateDto dto) {
        var category = useCase.getChangeName().change(id, dto);
        return ResponseEntity.ok().body(category);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable(value = "id") UUID id) {
        useCase.getDeactivate().deactivate(id);
        return ResponseEntity.noContent().build();
    }

}