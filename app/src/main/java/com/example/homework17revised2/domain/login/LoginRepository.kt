package com.example.homework17revised2.domain.login

import com.example.homework17revised2.common.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(loginRequest : LoginRequest) : Flow<Resource<LoginResponse>>
}