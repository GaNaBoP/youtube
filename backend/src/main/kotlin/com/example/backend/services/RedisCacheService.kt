package com.example.backend.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisCacheService {
    @Autowired
    private lateinit var stringRedisTemplate: StringRedisTemplate

    fun set(key: String, value: String, duration: Duration = Duration.ofHours(1)) {
        stringRedisTemplate.opsForValue().set(key, value, duration)
    }

    fun get(key: String): String? {
        return stringRedisTemplate.opsForValue().get(key)
    }
}