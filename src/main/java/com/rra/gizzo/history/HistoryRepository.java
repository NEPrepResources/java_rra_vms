package com.rra.gizzo.history;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HistoryRepository extends JpaRepository<VehicleOwnershipHistory, UUID> {

    List<VehicleOwnershipHistory> findByVehicleId(UUID vehicleId);

    List<VehicleOwnershipHistory> findByOldPlateNumberOrNewPlateNumberOrVehicleId(
            String oldPlateNumber, String newPlateNumber,UUID vehicleId);
}
