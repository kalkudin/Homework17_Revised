package com.example.homework17revised2.data.register.mapper

import com.example.homework17revised2.data.register.dto.RegisterResponseDto
import com.example.homework17revised2.domain.register.model.RegisterResponse

fun RegisterResponseDto.toDomain() : RegisterResponse {
    return RegisterResponse(id = id, token = token)
}