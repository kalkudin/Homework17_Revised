package com.example.homework17revised2.domain.register

import com.example.homework17revised2.data.resource.Resource
import com.example.homework17revised2.domain.register.model.RegisterRequest
import com.example.homework17revised2.domain.register.model.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(registerRequest : RegisterRequest) : Flow<Resource<RegisterResponse>>
}