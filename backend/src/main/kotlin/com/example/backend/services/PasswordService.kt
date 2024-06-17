package com.example.backend.services

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordService : PasswordEncoder {
    private val bcryptPasswordEncoder = BCryptPasswordEncoder()

    override fun encode(rawPassword: CharSequence?): String {
        return bcryptPasswordEncoder.encode(rawPassword.toString())
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        return bcryptPasswordEncoder.matches(rawPassword.toString(), encodedPassword.toString())
    }

}