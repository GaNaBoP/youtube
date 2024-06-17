package com.example.backend.dtos

import jakarta.validation.constraints.NotBlank

data class ConfirmEmailDto (
    @NotBlank
    var confirmationToken: String
)