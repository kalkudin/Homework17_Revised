package com.example.homework17revised2.data.register.mapper

import com.example.homework17revised2.data.register.dto.RegisterRequestDto
import com.example.homework17revised2.domain.register.model.RegisterRequest

fun RegisterRequest.toDto() : RegisterRequestDto{
    return RegisterRequestDto(email = email, password = password)
}