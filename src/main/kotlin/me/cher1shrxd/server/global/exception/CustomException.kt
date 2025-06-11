package me.cher1shrxd.server.global.exception

class CustomException(
    val code: CustomErrorCode
) : RuntimeException()
