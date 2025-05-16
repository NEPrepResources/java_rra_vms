package com.rra.gizzo.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InitiateResetPasswordDTO (

        @NotBlank(message = "Email required")
        @Email(message="Enter a valid email")
        String email

){

}
