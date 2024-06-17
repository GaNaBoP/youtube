package com.example.backend.repositories

import com.example.backend.models.ReviewModel
import com.example.backend.models.UserModel
import com.example.backend.models.VideoModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReviewRepository : JpaRepository<ReviewModel, Long> {
    fun findOneByUserAndVideo(user: UserModel, video: VideoModel): Optional<ReviewModel>
}