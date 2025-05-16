package com.rra.gizzo.owner.dto;

import java.util.UUID;

public record OwnerResponseDTO(
        UUID id,
        String fullNames,
        String nationalId,
        String phoneNumber,
        String address
) {
}
