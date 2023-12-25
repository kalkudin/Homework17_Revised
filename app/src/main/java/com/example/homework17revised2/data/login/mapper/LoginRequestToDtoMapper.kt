package com.example.homework17revised2.data.login.mapper

import com.example.homework17revised2.data.login.dto.LoginRequestDto
import com.example.homework17revised2.domain.login.LoginRequest

fun LoginRequest.toDto(): LoginRequestDto {
    return LoginRequestDto(email = email, password = password)
}