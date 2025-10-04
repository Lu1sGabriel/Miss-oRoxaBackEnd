package org.missao.roxa.missaoroxabackend.modules.products.presentation.contoller;

import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.modules.products.application.useCase.ProductUseCase;
import org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model.ProductCreateDto;
import org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model.ProductResponseDto;
import org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model.ProductUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase useCase;

    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@RequestBody ProductCreateDto dto) {
        var product = useCase.getCreate().create(dto);
        return ResponseEntity.ok().body(product);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDto> update(@PathVariable(value = "id") UUID id,
                                                     @RequestBody ProductUpdateDto dto) {
        var product = useCase.getUpdate().update(id, dto);
        return ResponseEntity.ok().body(product);
    }

}