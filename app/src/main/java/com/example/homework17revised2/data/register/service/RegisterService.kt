package com.example.homework17revised2.data.register.service

import com.example.homework17revised2.data.register.dto.RegisterRequestDto
import com.example.homework17revised2.data.register.dto.RegisterResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("register")
    suspend fun register(@Body request : RegisterRequestDto) : Response<RegisterResponseDto>
}