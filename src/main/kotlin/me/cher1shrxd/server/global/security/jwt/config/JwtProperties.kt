package me.cher1shrxd.server.global.security.jwt.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.jwt")
data class JwtProperties (
    var secretKey: String,
    var expiration: Long,
    var refreshExpiration: Long,
    var issuer: String
)