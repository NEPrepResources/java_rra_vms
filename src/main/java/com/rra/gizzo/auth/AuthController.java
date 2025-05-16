package com.rra.gizzo.auth;

import com.rra.gizzo.auth.dtos.*;
import com.rra.gizzo.email.EmailService;
import com.rra.gizzo.commons.exceptions.BadRequestException;
import com.rra.gizzo.user.UserService;
import com.rra.gizzo.user.dtos.UserResponseDTO;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController

@AllArgsConstructor

@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final OtpService otpService;
    private final EmailService emailService;

    @PostMapping("/register")
    @RateLimiter(name = "auth-rate-limiter")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody
                                                        RegisterRequestDTO user, UriComponentsBuilder uriBuilder){

        var userResponse = userService.createUser(user);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userResponse.id()).toUri();

        var otpToSend = otpService.generateOtp(userResponse.email(), OtpType.VERIFY_ACCOUNT);

        emailService.sendAccountVerificationEmail(userResponse.email(), userResponse.firstName(), otpToSend);

        return ResponseEntity.created(uri).body(userResponse);
    }

    @PatchMapping("/verify-account")
    @RateLimiter(name = "auth-rate-limiter")
    ResponseEntity<?> verifyAccount(@Valid @RequestBody VerifyAccountDTO verifyAccountRequest){

        if(!otpService.verifyOtp(verifyAccountRequest.email(), verifyAccountRequest.otp(), OtpType.VERIFY_ACCOUNT))
            throw new BadRequestException("Invalid email or OTP");

        userService.activateUserAccount(verifyAccountRequest.email());

        return ResponseEntity.ok("Account Activated successfully");
    }

    @PostMapping("/login")
    @RateLimiter(name = "auth-rate-limiter")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDto, HttpServletResponse response) {

        var loginResult = authService.login(loginRequestDto, response);

        return ResponseEntity.ok(new LoginResponseDTO(loginResult.accessToken()));
    }
    @PostMapping("/initiate-password-reset")
    ResponseEntity<?> initiatePasswordReset(@Valid @RequestBody InitiateResetPasswordDTO initiateRequest){

        var otpToSend = otpService.generateOtp(initiateRequest.email(), OtpType.RESET_PASSWORD);

        var user = userService.findByEmail(initiateRequest.email());

        emailService.sendResetPasswordOtp(user.getEmail(), user.getFirstName(), otpToSend);

        return ResponseEntity.ok("If your email is registered, you will receive an email with instructions to reset your password.");
    }

    @PatchMapping("/reset-password")
    @RateLimiter(name = "auth-rate-limiter")
    ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordRequest){

        if(!otpService.verifyOtp(resetPasswordRequest.email(), resetPasswordRequest.otp(), OtpType.RESET_PASSWORD))
            throw new BadRequestException("Invalid email or OTP");

        userService.changeUserPassword(resetPasswordRequest.email(), resetPasswordRequest.newPassword());

        return ResponseEntity.ok("Password reset went successfully you can login with your new password.");
    }
}
