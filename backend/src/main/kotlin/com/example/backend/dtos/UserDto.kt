package com.example.backend.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserDto (
    @Email(message = "Must be a valid email address")
    @NotBlank(message = "Email is required")
    var email: String,
    @NotBlank(message = "Name is required")
    var login: String,
    @NotBlank(message = "Password is required")
    var password: String,
)
