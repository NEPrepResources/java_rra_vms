package com.rra.gizzo.vehicle.dto;

import com.rra.gizzo.commons.validation.ValidPlateNumber;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record VehicleTransferRequest(
        @NotNull(message = "Vehicle ID is required")
        UUID vehicleId,

        @NotNull(message = "New owner ID is required")
        UUID newOwnerId,

        @NotNull
        @ValidPlateNumber
        String newPlateNumber,

        @NotNull(message = "Transfer price is required")
        Double purchasePrice
) {
}
