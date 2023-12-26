package com.example.homework17revised2.data.login.mapper

import com.example.homework17revised2.data.login.dto.LoginResponseDto
import com.example.homework17revised2.domain.login.model.LoginResponse

fun LoginResponseDto.toDomain() : LoginResponse {
    return LoginResponse(token = token)
}