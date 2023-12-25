package com.example.homework17revised2.data.login.service

import com.example.homework17revised2.data.login.dto.LoginRequestDto
import com.example.homework17revised2.data.login.dto.LoginResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    suspend fun login(@Body request : LoginRequestDto) : Response<LoginResponseDto>
}