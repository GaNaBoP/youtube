package com.example.backend.dtos

data class UpdateVideoDto (
    val videoId: Long,
    val newTitle: String,
    val newDescription: String,
)
