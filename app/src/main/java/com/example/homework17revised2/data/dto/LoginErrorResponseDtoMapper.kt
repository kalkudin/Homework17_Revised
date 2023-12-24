package com.example.homework17revised2.data.dto

import com.example.homework17revised2.domain.login.LoginErrorResponse

fun LoginErrorResponseDto.toDomain() : LoginErrorResponse{
    return LoginErrorResponse(error = error)
}