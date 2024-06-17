package com.example.backend.controllers

import com.example.backend.dtos.ConfirmEmailDto
import com.example.backend.dtos.UserDto
import com.example.backend.models.UserModel
import com.example.backend.services.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    @Autowired val userService: UserService
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody dto: UserDto): ResponseEntity<Any> {
        return try {
            userService.register(dto)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody dto: UserDto): ResponseEntity<Any> {
        return try {
            userService.login(dto)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/activate")
    fun activate(
        @RequestHeader(HttpHeaders.AUTHORIZATION) jwtToken: String,
        @Valid @RequestBody dto: ConfirmEmailDto
    ): ResponseEntity<Any> {
        return try {
            userService.activateAccount(jwtToken, dto.confirmationToken)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/refresh")
    fun refreshTokens(
        @RequestHeader(HttpHeaders.AUTHORIZATION) refreshToken: String
    ): ResponseEntity<Any> {
        return try {
            userService.refreshTokens(refreshToken)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.UNAUTHORIZED)
        }
    }

    @GetMapping("/getMe")
    fun getMe(
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String
    ): ResponseEntity<UserModel> {
        return try {
            userService.getMe(token)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}