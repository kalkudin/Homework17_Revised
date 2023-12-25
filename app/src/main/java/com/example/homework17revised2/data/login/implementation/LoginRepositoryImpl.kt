package com.example.homework17revised2.data.login.implementation

import android.net.http.HttpException
import com.example.homework17revised2.data.resource.Resource
import com.example.homework17revised2.data.login.dto.LoginErrorResponseDto
import com.example.homework17revised2.data.login.mapper.toDomain
import com.example.homework17revised2.data.login.mapper.toDto
import com.example.homework17revised2.data.login.service.LoginService
import com.example.homework17revised2.domain.login.LoginRepository
import com.example.homework17revised2.domain.login.LoginRequest
import com.example.homework17revised2.domain.login.LoginResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(val loginService: LoginService) : LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> {
        return flow {
            val response = loginService.login(loginRequest.toDto())
            try {
                if (response.isSuccessful) {
                    emit(Resource.Success(data = response.body()!!.toDomain()))
                }
                else {
                    val errorBody = response.errorBody()?.string() ?: ""
                    val errorResponseDto = parseErrorBody(errorBody)
                    val errorMessage = errorResponseDto?.toDomain()?.error ?: "Unknown error"
                    emit(Resource.Error(errorMessage = errorMessage))
                }
            }
            catch (e:IOException){
                emit(Resource.Error(errorMessage = e.message ?: "Something went wrong"))
            }
            catch (e:HttpException){
                emit(Resource.Error(errorMessage = e.message ?: "Please check your network connection"))
            }
            catch (e:Exception){
                emit(Resource.Error(errorMessage = "Something went wrong"))
            }
        }
    }

    private fun parseErrorBody(errorBody: String): LoginErrorResponseDto? {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val errorAdapter: JsonAdapter<LoginErrorResponseDto> = moshi.adapter(LoginErrorResponseDto::class.java)

        return try {
            errorAdapter.fromJson(errorBody)
        } catch (e: Exception) {
            null
        }
    }
}