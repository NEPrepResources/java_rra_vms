package com.rra.gizzo.platenumbers.mappers;

import com.rra.gizzo.platenumbers.PlateNumber;
import com.rra.gizzo.platenumbers.dto.PlateResponseDTO;
import com.rra.gizzo.platenumbers.dto.RegisterPlateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlateMapper {
    @Mapping(source = "issuedDate", target = "issuedDate", dateFormat = "yyyy-MM-dd")
    PlateResponseDTO toResponse(PlateNumber plate);

    @Mapping(target = "issuedDate", source = "issueDate")
    PlateNumber toEntity(RegisterPlateRequest request);
}