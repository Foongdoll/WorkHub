package com.foongdoll.portfolio.backend.auth.dto;

import com.foongdoll.portfolio.backend.auth.enums.Gender;

import java.time.Instant;
import java.time.LocalDate;

public record SignupDto(String email, String password, String name, LocalDate birthDay, Gender gender, String phone) {
}
