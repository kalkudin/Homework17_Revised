package com.example.homework17revised2.domain.login

import com.example.homework17revised2.data.resource.Resource
import com.example.homework17revised2.domain.login.model.LoginRequest
import com.example.homework17revised2.domain.login.model.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(loginRequest : LoginRequest) : Flow<Resource<LoginResponse>>
}