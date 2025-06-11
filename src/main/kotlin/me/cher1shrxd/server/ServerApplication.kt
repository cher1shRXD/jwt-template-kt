package me.cher1shrxd.server

import me.cher1shrxd.server.global.security.jwt.config.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@EnableConfigurationProperties(JwtProperties::class)
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}
