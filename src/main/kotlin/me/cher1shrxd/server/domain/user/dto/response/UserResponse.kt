package me.cher1shrxd.server.domain.user.dto.response

import me.cher1shrxd.server.domain.user.entity.UserEntity

data class UserResponse (
    val id: Long,
    val username: String,
)


fun UserEntity.toRes(): UserResponse {
    return UserResponse(
        id = this.id ?: 0,
        username = this.username,
    )
}