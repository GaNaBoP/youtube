package com.example.backend.dtos

import com.example.backend.enums.ReviewTypes
import jakarta.validation.constraints.NotBlank

data class CreateReviewDto (
    @NotBlank
    val videoId: Int,
    @NotBlank
    val reviewType: ReviewTypes
)
