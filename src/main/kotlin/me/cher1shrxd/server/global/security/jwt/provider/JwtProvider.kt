package me.cher1shrxd.server.global.security.jwt.provider

import io.jsonwebtoken.*
import jakarta.servlet.http.HttpServletRequest
import me.cher1shrxd.server.domain.auth.repository.RefreshTokenRepo
import me.cher1shrxd.server.domain.user.repository.UserRepo
import me.cher1shrxd.server.global.exception.CustomErrorCode
import me.cher1shrxd.server.global.exception.CustomException
import me.cher1shrxd.server.global.security.details.CustomUserDetails
import me.cher1shrxd.server.global.security.jwt.config.JwtProperties
import me.cher1shrxd.server.global.security.jwt.dto.JwtResponse
import me.cher1shrxd.server.global.security.jwt.enum.JwtType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
    private val userRepo: UserRepo,
    private val refreshTokenRepo: RefreshTokenRepo
) {
    private val key = SecretKeySpec(
        jwtProperties.secretKey.toByteArray(Charsets.UTF_8),
        Jwts.SIG.HS512.key().build().algorithm
    )

    fun generateToken(username: String): JwtResponse {
        val now = Date()

        val accessToken = Jwts.builder()
            .header()
            .type(JwtType.ACCESS.name)
            .and()
            .subject(username)
            .issuer(jwtProperties.issuer)
            .issuedAt(now)
            .expiration(Date(now.time + jwtProperties.expiration))
            .signWith(key)
            .compact()

        val refreshToken = Jwts.builder()
            .header()
            .type(JwtType.REFRESH.name)
            .and()
            .subject(username)
            .issuer(jwtProperties.issuer)
            .issuedAt(now)
            .expiration(Date(now.time + jwtProperties.refreshExpiration))
            .signWith(key)
            .compact()

        refreshTokenRepo.save(username, refreshToken)

        return JwtResponse(accessToken, refreshToken)
    }

    fun getUsername(token: String): String = getClaims(token).subject

    fun getAuthentication(token: String): Authentication {
        val claims = getClaims(token)
        val user = userRepo.findByUsername(claims.subject) ?: throw CustomException(CustomErrorCode.USER_NOT_FOUND)
        val details = CustomUserDetails(user)

        return UsernamePasswordAuthenticationToken(details, null, details.authorities)
    }

    fun extractToken(request: HttpServletRequest) =
        request.getHeader("Authorization")?.removePrefix("Bearer ")

    fun getType(token: String) = JwtType.valueOf(
        Jwts.parser()
            .verifyWith(key)
            .requireIssuer(jwtProperties.issuer)
            .build()
            .parseSignedClaims(token)
            .header.type
    )

    private fun getClaims(token: String): Claims {
        try {
            return Jwts.parser()
                .verifyWith(key)
                .requireIssuer(jwtProperties.issuer)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: ExpiredJwtException) {
            throw CustomException(CustomErrorCode.EXPIRED_JWT_TOKEN)
        } catch (e: UnsupportedJwtException) {
            throw CustomException(CustomErrorCode.UNSUPPORTED_JWT_TOKEN)
        } catch (e: MalformedJwtException) {
            throw CustomException(CustomErrorCode.MALFORMED_JWT_TOKEN)
        } catch (e: SecurityException) {
            throw CustomException(CustomErrorCode.INVALID_JWT_TOKEN)
        } catch (e: IllegalArgumentException) {
            throw CustomException(CustomErrorCode.INVALID_JWT_TOKEN)
        }
    }
}
