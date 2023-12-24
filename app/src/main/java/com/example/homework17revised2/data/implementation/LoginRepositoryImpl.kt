package com.example.homework17revised2.data.implementation

import android.net.http.HttpException
import android.util.Log.d
import com.example.homework17revised2.common.Resource
import com.example.homework17revised2.data.dto.toDomain
import com.example.homework17revised2.data.dto.toDto
import com.example.homework17revised2.data.service.LoginService
import com.example.homework17revised2.domain.login.LoginRepository
import com.example.homework17revised2.domain.login.LoginRequest
import com.example.homework17revised2.domain.login.LoginResponse
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
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
                    emit(Resource.Error(errorMessage = response.errorBody()?.toString() ?: ""))
                }
            }
            catch (e:IOException){
                d("LoginRepositoryImplementation", "${e.message}")
            }
            catch (e:HttpException){
                d("LoginRepositoryImplementation", "${e.message}")
            }
            catch (e:Exception){
                d("LoginRepositoryImplementation", "${e.message}")
            }
        }
    }
}