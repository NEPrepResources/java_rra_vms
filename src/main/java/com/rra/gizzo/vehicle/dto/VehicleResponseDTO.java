package com.rra.gizzo.vehicle.dto;

import java.util.UUID;

public record VehicleResponseDTO(
        UUID id,
        String chassisNumber,
        String manufactureCompany,
        Integer manufactureYear,
        Double price,
        String modelName,
        UUID currentOwnerId,
        String plateNumber
) {
}
