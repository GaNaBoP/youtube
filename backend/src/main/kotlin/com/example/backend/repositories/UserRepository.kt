package com.example.backend.repositories

import com.example.backend.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserModel, Long> {
    fun existsByEmailAndLogin(email: String, login: String): Boolean
    fun findByEmailAndLogin(email: String, login: String): UserModel?
}