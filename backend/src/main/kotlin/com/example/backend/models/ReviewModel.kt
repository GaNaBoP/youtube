package com.example.backend.models

import com.example.backend.enums.ReviewTypes
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "reviews")
open class ReviewModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    open var id: Long? = null

    @Column(name = "review")
    open lateinit var reviewType: ReviewTypes

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    open lateinit var user: UserModel

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    @JsonBackReference
    open lateinit var video: VideoModel
}