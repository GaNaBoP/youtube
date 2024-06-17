package com.example.backend.services

import com.example.backend.enums.TokenTypes
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService {
    @Value("\${jwt.accessSecretKey}")
    private lateinit var accessSecretKey: String

    @Value("\${jwt.refreshSecretKey}")
    private lateinit var refreshSecretKey: String

    fun generateToken(userId: Long, type: TokenTypes): String {
        val claims = mutableMapOf<String, Any>()
        claims["userId"] = userId

        return Jwts.builder()
            .claims(claims)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(
                    System.currentTimeMillis() +
                            if (type == TokenTypes.ACCESS_TOKEN) 24 * 60 * 60 * 1000
                            else 20 * 24 * 60 * 60 * 1000
            ))
            .signWith(getSigningKey(type))
            .compact()
    }

    fun verify(tokenString: String, type: TokenTypes = TokenTypes.ACCESS_TOKEN): Long {
        val token = tokenString.split(" ")[1]
        val payload = Jwts.parser().verifyWith(getSigningKey(type)).build().parseSignedClaims(token).payload
        return payload["userId"].toString().toLong()
    }

    private fun getSigningKey(type: TokenTypes): SecretKey {
        val keyBytes = Decoders.BASE64.decode(
            if (type == TokenTypes.ACCESS_TOKEN) accessSecretKey
            else refreshSecretKey
        )
        return Keys.hmacShaKeyFor(keyBytes)
    }
}