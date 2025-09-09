package org.missao.roxa.missaoroxabackend.modules.address.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.missao.roxa.missaoroxabackend.modules.address.application.useCase.AddressUseCase;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
@Tag(name = "Addresses", description = "Endpoints for managing addresses")
public class AddressController {
    private final AddressUseCase useCase;

    public AddressController(AddressUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/{user-id}")
    @Operation(
            summary = "Get address by user ID",
            description = "Fetch the address associated with a specific user by their unique identifier"
    )
    public ResponseEntity<AddressResponseDto> getByUser(@PathVariable("user-id") UUID userId) {
        var address = useCase.find().byUser(userId);
        return ResponseEntity.ok(address);
    }

    @GetMapping("postal-code/{postal-code}")
    @Operation(
            summary = "Get addresses by postal code",
            description = "Retrieve a paginated list of addresses filtered by postal code. "
                    + "Supports pagination and sorting."
    )
    public ResponseEntity<Page<AddressResponseDto>> getByPostalCode(
            @PathVariable("postal-code") String postalCode,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "page-size", defaultValue = "20") int pageSize,
            @RequestParam(value = "sort", defaultValue = "asc") String sort) {
        var addresses = useCase.find().byPostalCode(postalCode, page, pageSize, sort);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("municipality/{municipality}")
    @Operation(
            summary = "Get addresses by municipality ID",
            description = "Retrieve a paginated list of addresses filtered by the municipality ID. "
                    + "Supports pagination and sorting."
    )
    public ResponseEntity<Page<AddressResponseDto>> getByMunicipality(
            @PathVariable("municipality") UUID municipality,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "page-size", defaultValue = "20") int pageSize,
            @RequestParam(value = "sort", defaultValue = "asc") String sort) {
        var addresses = useCase.find().byMunicipality(municipality, page, pageSize, sort);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("state-name/{state}")
    @Operation(
            summary = "Get addresses by state name",
            description = "Retrieve a paginated list of addresses filtered by state name. "
                    + "Supports pagination and sorting."
    )
    public ResponseEntity<Page<AddressResponseDto>> getByStateName(
            @PathVariable("state") String state,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "page-size", defaultValue = "20") int pageSize,
            @RequestParam(value = "sort", defaultValue = "asc") String sort) {
        var addresses = useCase.find().byState(state, page, pageSize, sort);
        return ResponseEntity.ok(addresses);
    }

}