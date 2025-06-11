package me.cher1shrxd.server.global.exception

import org.springframework.http.ResponseEntity

data class CustomErrorResponse(
    val status: Int,
    val message: String
) {
    companion object {
        fun of(code: CustomErrorCode): CustomErrorResponse {
            return CustomErrorResponse(
                status = code.status,
                message = code.message
            )
        }
    }

    fun toEntity(): ResponseEntity<CustomErrorResponse> {
        return ResponseEntity.status(status).body(this)
    }
}
