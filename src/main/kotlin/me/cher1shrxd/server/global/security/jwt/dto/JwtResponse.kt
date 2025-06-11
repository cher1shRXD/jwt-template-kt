package me.cher1shrxd.server.global.security.jwt.dto

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String,
)
