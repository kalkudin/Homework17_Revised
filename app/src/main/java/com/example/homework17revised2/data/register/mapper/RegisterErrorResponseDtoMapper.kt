package com.example.homework17revised2.data.register.mapper

import com.example.homework17revised2.data.register.dto.RegisterErrorResponseDto
import com.example.homework17revised2.domain.register.model.RegisterErrorResponse

fun RegisterErrorResponseDto.toDomain() : RegisterErrorResponse{
    return RegisterErrorResponse(error = error)
}