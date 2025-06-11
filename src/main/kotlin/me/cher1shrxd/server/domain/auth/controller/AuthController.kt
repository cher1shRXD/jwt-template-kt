package me.cher1shrxd.server.domain.auth.controller

import io.swagger.v3.oas.annotations.tags.Tag
import me.cher1shrxd.server.domain.auth.dto.request.Login
import me.cher1shrxd.server.domain.auth.dto.request.Reissue
import me.cher1shrxd.server.domain.auth.dto.request.Signup
import me.cher1shrxd.server.domain.auth.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
@Tag(name = "AUTH")
class AuthController(
    val authService: AuthService,
) {
    @PostMapping("/login")
    fun login(@RequestBody request: Login) = authService.login(request)

    @PostMapping("/signup")
    fun signup(@RequestBody request: Signup) = authService.signup(request)

    @PostMapping("/reissue")
    fun reissue(@RequestBody request: Reissue) = authService.reissue(request)
}