package me.cher1shrxd.server.domain.auth.service

import jakarta.transaction.Transactional
import me.cher1shrxd.server.domain.auth.dto.request.Login
import me.cher1shrxd.server.domain.auth.dto.request.Reissue
import me.cher1shrxd.server.domain.auth.dto.request.Signup
import me.cher1shrxd.server.domain.auth.repository.RefreshTokenRepo
import me.cher1shrxd.server.domain.user.entity.UserEntity
import me.cher1shrxd.server.domain.user.repository.UserRepo
import me.cher1shrxd.server.global.exception.CustomErrorCode
import me.cher1shrxd.server.global.exception.CustomException
import me.cher1shrxd.server.global.security.jwt.dto.JwtResponse
import me.cher1shrxd.server.global.security.jwt.enum.JwtType
import me.cher1shrxd.server.global.security.jwt.provider.JwtProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
@Transactional
class AuthService (
    private val jwtProvider: JwtProvider,
    private val userRepo: UserRepo,
    private val refreshTokenRepo: RefreshTokenRepo
) {
    val passwordEncoder = BCryptPasswordEncoder()

    fun login(request: Login): JwtResponse {
        val isExist = userRepo.existsByUsername(request.username)

        if(!isExist) {
           throw CustomException(CustomErrorCode.USER_NOT_FOUND)
        }

        val user = userRepo.findByUsername(request.username)

        if(!passwordEncoder.matches(request.password, user?.password)) {
            throw CustomException(CustomErrorCode.WRONG_PASSWORD)
        }

        val token = jwtProvider.generateToken(request.username)
        refreshTokenRepo.save(request.username, token.refreshToken)

        return token
    }

    fun signup(request: Signup): JwtResponse {
        val isExist = userRepo.existsByUsername(request.username)

        if(isExist) {
            throw CustomException(CustomErrorCode.USERNAME_DUPLICATION)
        }

        val user = UserEntity(
            username = request.username,
            password = passwordEncoder.encode(request.password),
        )

        userRepo.save(user)

        val token = jwtProvider.generateToken(request.username)
        refreshTokenRepo.save(request.username, token.refreshToken)

        return token
    }

    fun reissue(request: Reissue): JwtResponse {
        if (jwtProvider.getType(request.refreshToken) != JwtType.REFRESH) {
            throw CustomException(CustomErrorCode.INVALID_TOKEN_TYPE)
        }

        val username = jwtProvider.getUsername(request.refreshToken)
        val isExist = refreshTokenRepo.existsByUsername(username)

        if(!isExist) {
            throw CustomException(CustomErrorCode.EXPIRED_JWT_TOKEN)
        }

        val token = jwtProvider.generateToken(username)
        refreshTokenRepo.save(username, token.refreshToken)

        return token
    }
}