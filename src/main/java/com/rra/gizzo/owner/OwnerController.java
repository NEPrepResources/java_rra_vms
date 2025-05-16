package com.rra.gizzo.owner;

import com.rra.gizzo.commons.exceptions.BadRequestException;
import com.rra.gizzo.owner.dto.OwnerResponseDTO;
import com.rra.gizzo.owner.dto.PlateNumberResponseDTO;
import com.rra.gizzo.owner.dto.RegisterOwnerRequestDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/owners")
@AllArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<OwnerResponseDTO> createOwner(@Valid @RequestBody RegisterOwnerRequestDTO ownerRequest) {
        OwnerResponseDTO createdOwner = ownerService.createOwner(ownerRequest);
        return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
    }

    @GetMapping("/{ownerId}/plate-numbers")
    public ResponseEntity<List<PlateNumberResponseDTO>> getPlateNumberByOwner(@PathVariable UUID ownerId) {
        return new ResponseEntity<>(ownerService.getPlateNumbersByOwner(ownerId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<OwnerResponseDTO>> searchOwners(
            @RequestParam(required = false) String nationalId,
            @RequestParam(required = false) String phoneNumber) {

        // Call the service to get the list of owners, which will handle the validation
        try {
            List<OwnerResponseDTO> owners = ownerService.searchOwners(nationalId, phoneNumber);
            return ResponseEntity.ok(owners);
        } catch (BadRequestException ex) {
            // Handle invalid request where both or neither are passed
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<OwnerResponseDTO>> getAllOwners() {
        List<OwnerResponseDTO> owners = ownerService.getAllOwners();
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }

}
