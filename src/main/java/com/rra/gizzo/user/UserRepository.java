package com.rra.gizzo.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmailOrNationalIdOrPhoneNumber(String email, String nationalId, String phoneNumber);
    Optional<User> findByEmail(String email);
}

//checking the existance of the user and if exist we fetch their data, options means it can have value or be null. So findbyEmail is optional