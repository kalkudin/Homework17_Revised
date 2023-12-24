package com.example.homework17revised2.data.dto

import com.example.homework17revised2.domain.login.LoginResponse

fun LoginResponseDto.toDomain() : LoginResponse {
    return LoginResponse(token = token)
}