package me.cher1shrxd.server.global.security.jwt.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import me.cher1shrxd.server.global.security.jwt.provider.JwtProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider
) : GenericFilterBean() {

    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        val token = jwtProvider.extractToken(servletRequest as HttpServletRequest)

        if (token != null) {
            val authentication = jwtProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(servletRequest, servletResponse)
    }
}
