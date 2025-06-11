package me.cher1shrxd.server.domain.user.controller

import io.swagger.v3.oas.annotations.tags.Tag
import me.cher1shrxd.server.domain.user.dto.response.UserResponse
import me.cher1shrxd.server.domain.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
@Tag(name = "USER")
class UserController(
    val userService: UserService
) {
    @GetMapping("/me")
    fun me(): UserResponse = userService.getMe()
}