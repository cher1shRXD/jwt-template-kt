package me.cher1shrxd.server.domain.auth.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RefreshTokenRepo(
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun save(username: String, refreshToken: String) {
        redisTemplate.opsForValue().set("refreshToken:$username", refreshToken)
    }


    fun findByUsername(username: String): String? {
        return redisTemplate.opsForValue().get("refreshToken:$username")
    }

    fun existsByUsername(username: String): Boolean {
        return redisTemplate.hasKey("refreshToken:$username")
    }
}
