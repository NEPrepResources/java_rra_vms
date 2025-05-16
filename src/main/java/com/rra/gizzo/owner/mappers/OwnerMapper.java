package com.rra.gizzo.owner.mappers;

import com.rra.gizzo.owner.Owner;
import com.rra.gizzo.owner.dto.RegisterOwnerRequestDTO;
import com.rra.gizzo.owner.dto.OwnerResponseDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface OwnerMapper {

    @Mapping(target = "fullNames", source = "fullNames")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    Owner toEntity(RegisterOwnerRequestDTO request);
    OwnerResponseDTO toResponse(Owner owner);
}
