package me.cher1shrxd.server.global.security

import me.cher1shrxd.server.domain.user.entity.UserEntity
import me.cher1shrxd.server.domain.user.repository.UserRepo
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component


@Component
class SecurityHolder(
    private val userRepo: UserRepo,
) {
    val user: UserEntity?
        get() = SecurityContextHolder.getContext().authentication?.name?.let { username ->
            userRepo.findByUsername(username)
        }
}