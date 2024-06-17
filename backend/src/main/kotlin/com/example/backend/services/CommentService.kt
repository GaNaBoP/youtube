package com.example.backend.services

import com.example.backend.dtos.CreateCommentDto
import com.example.backend.repositories.CommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentService (
    @Autowired private val jwtService: JwtService,
    @Autowired private val commentRepository: CommentRepository
) {
    fun createComment(dto: CreateCommentDto, token: String) {
        val userId = jwtService.verify(token)
        commentRepository.create(dto.videoId, userId.toInt(), dto.content)
    }

    fun deleteComment(commentId: Int) {
        commentRepository.deleteById(commentId.toLong())
    }
}