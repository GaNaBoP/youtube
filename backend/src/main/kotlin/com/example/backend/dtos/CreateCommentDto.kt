package com.example.backend.dtos

import jakarta.validation.constraints.NotBlank

data class CreateCommentDto (
    @NotBlank
    val videoId: Int,
    @NotBlank
    val content: String
)
