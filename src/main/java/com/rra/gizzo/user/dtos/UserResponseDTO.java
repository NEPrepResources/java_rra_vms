package com.rra.gizzo.user.dtos;

import java.util.UUID;
//Universally Unique Identifier
public record UserResponseDTO(
        UUID id,
        String firstName,
        String lastName,
        String email

) {
}

//dto data Transfer Object : it is a design pattern used to transfer data between different layers e.i from db especially the ones we validate from the user input