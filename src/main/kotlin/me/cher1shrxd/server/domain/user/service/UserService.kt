package me.cher1shrxd.server.domain.user.service

import jakarta.transaction.Transactional
import me.cher1shrxd.server.domain.user.dto.response.UserResponse
import me.cher1shrxd.server.domain.user.dto.response.toRes
import me.cher1shrxd.server.domain.user.repository.UserRepo
import me.cher1shrxd.server.global.exception.CustomErrorCode
import me.cher1shrxd.server.global.exception.CustomException
import me.cher1shrxd.server.global.security.SecurityHolder
import org.springframework.stereotype.Service

@Service
@Transactional
class UserService (
    private val userRepo: UserRepo,
    private val securityHolder: SecurityHolder
) {
    fun getMe(): UserResponse {
        val username = securityHolder.user?.username ?: throw CustomException(CustomErrorCode.USER_NOT_FOUND)
        val user = userRepo.findByUsername(username) ?: throw CustomException(CustomErrorCode.USER_NOT_FOUND)

        return user.toRes()
    }
}