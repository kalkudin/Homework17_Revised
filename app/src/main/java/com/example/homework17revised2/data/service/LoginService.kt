package com.example.homework17revised2.data.service

import com.example.homework17revised2.data.dto.LoginRequestDto
import com.example.homework17revised2.data.dto.LoginResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    suspend fun login(@Body request : LoginRequestDto) : Response<LoginResponseDto>
}