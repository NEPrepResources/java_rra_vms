package com.rra.gizzo.owner;

import com.rra.gizzo.commons.exceptions.BadRequestException;
import com.rra.gizzo.owner.dto.OwnerResponseDTO;
import com.rra.gizzo.owner.dto.PlateNumberResponseDTO;
import com.rra.gizzo.owner.dto.RegisterOwnerRequestDTO;
import com.rra.gizzo.owner.mappers.OwnerMapper;
import com.rra.gizzo.vehicle.Vehicle;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    public OwnerResponseDTO createOwner(RegisterOwnerRequestDTO ownerRequest) {
        if (ownerRepository.existsByNationalIdOrPhoneNumber(ownerRequest.nationalId(), ownerRequest.phoneNumber())) {
            throw new BadRequestException("Owner with this National ID or Phone Number already exists");
        }

        // Map DTO to Entity
        Owner newOwner = ownerMapper.toEntity(ownerRequest);

        // Save the new owner
        ownerRepository.save(newOwner);

        // Map saved owner to response DTO
        return ownerMapper.toResponse(newOwner);
    }

//     Search owners by National ID, or Phone
    public List<OwnerResponseDTO> searchOwners(String nationalId, String phoneNumber) {
        List<Owner> owners;
        if (nationalId != null) {
            owners = ownerRepository.findByNationalId(nationalId);
        } else if (phoneNumber != null) {
            owners = ownerRepository.findByPhoneNumber(phoneNumber);
        } else {
            owners = ownerRepository.findAll();
        }
        return owners.stream().map(owner ->
                new OwnerResponseDTO(
                        owner.getId(),
                        owner.getFullNames(),
                        owner.getNationalId(),
                        owner.getPhoneNumber(),
                        owner.getAddress())).collect(Collectors.toList());
    }


    // Display plate numbers associated with a given owner
    public List<PlateNumberResponseDTO> getPlateNumbersByOwner(UUID ownerId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new BadRequestException("Owner not found"));
        List<Vehicle> plateNumbers = owner.getVehicles();
        return plateNumbers.stream()
                .map(plateNumber -> new PlateNumberResponseDTO(plateNumber.getPlateNumber().getPlateNumber()))
                .collect(Collectors.toList());
    }

    public List<OwnerResponseDTO> getAllOwners() {
        List<Owner> owners = ownerRepository.findAll();
        return owners.stream()
                .map(owner -> new OwnerResponseDTO(
                        owner.getId(),
                        owner.getFullNames(),
                        owner.getNationalId(),
                        owner.getPhoneNumber(),
                        owner.getAddress()
                ))
                .collect(Collectors.toList());
    }

}
