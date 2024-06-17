package com.example.backend.models

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "users")
open class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    open var id: Long? = null

    @Column(name = "email", nullable = false)
    open lateinit var email: String

    @Column(name = "login", nullable = false)
    open lateinit var login: String

    @Column(name = "password", nullable = false)
    open lateinit var password: String

    @Column(name = "isActivated")
    open var isActivated: Boolean = false

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL])
    @JsonManagedReference
    open var videos: MutableList<VideoModel> = mutableListOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL])
    @JsonManagedReference
    open var comments: MutableList<CommentModel> = mutableListOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL])
    @JsonManagedReference
    open var reviews: MutableList<ReviewModel> = mutableListOf()
}