package me.cher1shrxd.server.global.security.jwt.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.cher1shrxd.server.global.exception.CustomErrorCode
import me.cher1shrxd.server.global.exception.CustomException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtExceptionFilter : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest, 
        response: HttpServletResponse, 
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: CustomException) {
            sendErrorResponse(response, e)
        }
    }

    @Throws(IOException::class)
    private fun sendErrorResponse(response: HttpServletResponse, e: CustomException) {
        val code: CustomErrorCode = e.code

        response.status = code.status
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"

        val mapper = ObjectMapper()
        val map = mapOf(
            "message" to code.message,
            "status" to code.status
        )

        response.writer.write(mapper.writeValueAsString(map))
    }
}
