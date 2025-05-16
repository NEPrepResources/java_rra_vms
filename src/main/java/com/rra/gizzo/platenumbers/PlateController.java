package com.rra.gizzo.platenumbers;

import com.rra.gizzo.platenumbers.dto.PlateResponseDTO;
import com.rra.gizzo.platenumbers.dto.RegisterPlateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/plates")
@RequiredArgsConstructor
public class PlateController {

    private final PlateService plateNumberService;

    // Register a new plate number (with no owner)
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PlateResponseDTO> registerPlate(@Valid @RequestBody RegisterPlateRequest request) {
        PlateResponseDTO response = plateNumberService.registerPlate(request);
        return ResponseEntity.ok(response);
    }

}
