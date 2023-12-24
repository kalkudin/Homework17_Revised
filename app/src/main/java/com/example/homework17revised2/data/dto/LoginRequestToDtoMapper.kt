package com.example.homework17revised2.data.dto

import com.example.homework17revised2.domain.login.LoginRequest

fun LoginRequest.toDto(): LoginRequestDto {
    return LoginRequestDto(email = email, password = password)
}