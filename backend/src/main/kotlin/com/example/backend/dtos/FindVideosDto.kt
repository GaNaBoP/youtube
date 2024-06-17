package com.example.backend.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class FindVideosDto(
    @NotBlank
    @NotNull
    var search: String
)
