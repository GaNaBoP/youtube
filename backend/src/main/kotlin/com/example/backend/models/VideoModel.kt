package com.example.backend.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "videos")
open class VideoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    open var id: Long? = null

    @Column(name = "title", nullable = false)
    open lateinit var title: String

    @Column(name = "description", nullable = false)
    open lateinit var description: String

    @Column(name = "videoUrl", nullable = false)
    open lateinit var videoUrl: String

    @Column(name = "previewUrl", nullable = false)
    open lateinit var previewUrl: String

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    open lateinit var user: UserModel

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "video", cascade = [CascadeType.ALL])
    @JsonManagedReference
    open var comments: MutableList<CommentModel> = mutableListOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "video", cascade = [CascadeType.ALL])
    @JsonManagedReference
    open var reviews: MutableList<ReviewModel> = mutableListOf()
}