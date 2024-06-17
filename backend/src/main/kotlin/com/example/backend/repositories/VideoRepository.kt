package com.example.backend.repositories

import com.example.backend.models.VideoModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface VideoRepository : JpaRepository<VideoModel, Long> {

    @Query("""
        update videos 
        set title = :newTitle,
            description = :newDescription
        where id=:videoId
        returning *
    """, nativeQuery = true)
    fun updateVideoById(
        @Param("videoId") videoId: Long,
        @Param("newTitle") newTitle: String,
        @Param("newDescription") newDescription: String,
    ): VideoModel

    @Query("""
        select * from videos
        where title like concat(:string, '%')
        limit 10
    """, nativeQuery = true)
    fun findVideosByPartOfTitle(
        @Param("string") string: String
    ): List<VideoModel>
}