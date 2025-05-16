package com.rra.gizzo.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidJWTException extends AuthenticationException {
    public InvalidJWTException(String message) {
        super(message);
    }
}
