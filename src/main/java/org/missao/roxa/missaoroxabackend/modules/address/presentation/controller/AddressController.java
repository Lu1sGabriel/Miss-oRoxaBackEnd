package org.missao.roxa.missaoroxabackend.modules.address.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.missao.roxa.missaoroxabackend.modules.address.application.useCase.AddressUseCase;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.*;
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
        var address = useCase.getFind().byUser(userId);
        return ResponseEntity.ok().body(address);
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
        var addresses = useCase.getFind().byPostalCode(postalCode, page, pageSize, sort);
        return ResponseEntity.ok().body(addresses);
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
        var addresses = useCase.getFind().byMunicipality(municipality, page, pageSize, sort);
        return ResponseEntity.ok().body(addresses);
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
        var addresses = useCase.getFind().byState(state, page, pageSize, sort);
        return ResponseEntity.ok().body(addresses);
    }

    @PatchMapping(value = "/postal-code/{user-id}")
    @Operation(
            summary = "Update postal code of an address",
            description = "Change the postal code of the address associated with the specified user ID"
    )
    public ResponseEntity<AddressResponseDto> changePostalCode(
            @Parameter(description = "User ID whose address postal code will be updated")
            @PathVariable(value = "user-id") UUID userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO containing the new postal code",
                    required = true
            )
            @RequestBody AddressChangePostalCodeDto dto) {
        var address = useCase.getChangePostalCode().change(userId, dto);
        return ResponseEntity.ok().body(address);
    }

    @PatchMapping(value = "/street/{user-id}")
    @Operation(
            summary = "Update street of an address",
            description = "Change the street of the address associated with the specified user ID"
    )
    public ResponseEntity<AddressResponseDto> changeStreet(
            @Parameter(description = "User ID whose address street will be updated")
            @PathVariable(value = "user-id") UUID userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO containing the new street",
                    required = true
            )
            @RequestBody AddressChangeStreetDto dto) {
        var address = useCase.getChangeStreet().change(userId, dto);
        return ResponseEntity.ok().body(address);
    }

    @PatchMapping(value = "/complement/{user-id}")
    @Operation(
            summary = "Update complement of an address",
            description = "Change the complement of the address associated with the specified user ID"
    )
    public ResponseEntity<AddressResponseDto> changeComplement(
            @Parameter(description = "User ID whose address complement will be updated")
            @PathVariable(value = "user-id") UUID userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO containing the new complement",
                    required = true
            )
            @RequestBody AddressChangeComplementDto dto) {
        var address = useCase.getChangeComplement().change(userId, dto);
        return ResponseEntity.ok().body(address);
    }

    @PatchMapping(value = "/neighborhood/{user-id}")
    @Operation(
            summary = "Update neighborhood of an address",
            description = "Change the neighborhood of the address associated with the specified user ID"
    )
    public ResponseEntity<AddressResponseDto> changeNeighborhood(
            @Parameter(description = "User ID whose address neighborhood will be updated")
            @PathVariable(value = "user-id") UUID userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO containing the new neighborhood",
                    required = true
            )
            @RequestBody AddressChangeNeighborhoodDto dto) {
        var address = useCase.getChangeNeighborhood().change(userId, dto);
        return ResponseEntity.ok().body(address);
    }

}