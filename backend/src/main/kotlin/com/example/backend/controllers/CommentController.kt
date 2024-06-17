package com.example.backend.controllers

import com.example.backend.dtos.CreateCommentDto
import com.example.backend.services.CommentService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController (
    @Autowired private val commentService: CommentService
) {

    @PostMapping("/create")
    fun createComment(
        @Valid @RequestBody dto: CreateCommentDto,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String
    ) {
        return commentService.createComment(dto, token)
    }

    @DeleteMapping("/{id}")
    fun deleteComment(id: Int) {
        return commentService.deleteComment(id)
    }
}