package me.cher1shrxd.server.global.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<CustomErrorResponse> {
        return CustomErrorResponse.of(e.code).toEntity()
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<CustomErrorResponse> {
        return CustomErrorResponse.of(CustomErrorCode.METHOD_ARGUMENT_NOT_SUPPORTED).toEntity()
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(e: NoHandlerFoundException): ResponseEntity<CustomErrorResponse> {
        return CustomErrorResponse.of(CustomErrorCode.NO_HANDLER_FOUND).toEntity()
    }
}
