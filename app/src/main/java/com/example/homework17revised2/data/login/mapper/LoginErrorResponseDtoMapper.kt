package com.example.homework17revised2.data.login.mapper

import com.example.homework17revised2.data.login.dto.LoginErrorResponseDto
import com.example.homework17revised2.domain.login.model.LoginErrorResponse

fun LoginErrorResponseDto.toDomain() : LoginErrorResponse {
    return LoginErrorResponse(error = error)
}