package com.example.backend.repositories

import com.example.backend.models.CommentModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CommentRepository : JpaRepository<CommentModel, Long> {
    @Query("""
        insert into comments (video_id, user_id, content)
        values (:videoId, :userId, :content)
        returning *
    """, nativeQuery = true)
    fun create(
        @Param("videoId") videoId: Int,
        @Param("userId") userId: Int,
        @Param("content") content: String
    ): CommentModel
}