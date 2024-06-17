package com.example.backend.services

import com.example.backend.dtos.AuthDto
import com.example.backend.dtos.UserDto
import com.example.backend.enums.TokenTypes
import com.example.backend.models.UserModel
import com.example.backend.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService (
    @Autowired private val userRepository: UserRepository,
    @Autowired private val passwordService: PasswordService,
    @Autowired private val jwtService: JwtService,
    @Autowired private val emailService: EmailService,
    @Autowired private val redisCacheService: RedisCacheService,
) {
    fun register(dto: UserDto): ResponseEntity<Any> {
        if (userRepository.existsByEmailAndLogin(dto.email, dto.login)) {
            throw Exception("User already exists")
        }
        val newUser = UserModel()
        val hashedPassword = passwordService.encode(dto.password)

        newUser.email = dto.email
        newUser.login = dto.login
        newUser.password = hashedPassword
        val user = userRepository.save(newUser)

        val confirmationToken = UUID.randomUUID().toString()
        emailService.sendEmail(user.email, "Email confirmation", confirmationToken)
        redisCacheService.set(user.id.toString(), confirmationToken)

        val response = AuthDto()
        response.accessToken = jwtService.generateToken(user.id!!, TokenTypes.ACCESS_TOKEN)
        response.refreshToken = jwtService.generateToken(user.id!!, TokenTypes.REFRESH_TOKEN)
        return ResponseEntity(response, HttpStatus.OK)
    }

    fun login(dto: UserDto): ResponseEntity<Any> {
        val user = userRepository.findByEmailAndLogin(dto.email, dto.login) ?: throw Exception("User not found")
        if (!user.isActivated) { throw Exception("User not activated") }

        val isPasswordMatched = passwordService.matches(dto.password, user.password)
        if (!isPasswordMatched) { throw Exception("Passwords do not match") }

        val response = AuthDto()
        response.accessToken = jwtService.generateToken(user.id!!, TokenTypes.ACCESS_TOKEN)
        response.refreshToken = jwtService.generateToken(user.id!!, TokenTypes.REFRESH_TOKEN)
        return ResponseEntity(response, HttpStatus.OK)
    }

    fun activateAccount(jwtToken: String, confirmationToken: String): ResponseEntity<Any> {
        val userId = jwtService.verify(jwtToken, TokenTypes.ACCESS_TOKEN)
        val user = userRepository.findById(userId).get()

        if (redisCacheService.get(user.id.toString()) != confirmationToken) {
            throw Exception("Invalid key")
        }

        user.isActivated = true
        userRepository.save(user)

        return ResponseEntity("Successfully activated", HttpStatus.OK)
    }

    fun refreshTokens(refreshToken: String): ResponseEntity<Any> {
        val userId = jwtService.verify(refreshToken, TokenTypes.REFRESH_TOKEN)
        val response = AuthDto()
        response.accessToken = jwtService.generateToken(userId, TokenTypes.ACCESS_TOKEN)
        response.refreshToken = jwtService.generateToken(userId, TokenTypes.REFRESH_TOKEN)
        return ResponseEntity(response, HttpStatus.OK)
    }

    fun getMe(token: String): ResponseEntity<UserModel> {
        val userId = jwtService.verify(token)
        val user = userRepository.findById(userId).get()
        if (!user.isActivated) { throw Exception("User not activated") }
        return ResponseEntity(user, HttpStatus.OK)
    }
}