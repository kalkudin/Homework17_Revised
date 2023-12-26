package com.example.homework17revised2.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework17revised2.data.resource.Resource
import com.example.homework17revised2.domain.datastore.UserDataStoreRepository
import com.example.homework17revised2.domain.login.LoginRepository
import com.example.homework17revised2.domain.login.LoginRequest
import com.example.homework17revised2.domain.login.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userDataStoreRepository: UserDataStoreRepository
) : ViewModel() {

    private val _loginFlow = MutableSharedFlow<Resource<LoginResponse>>()
    val loginFlow: SharedFlow<Resource<LoginResponse>> = _loginFlow.asSharedFlow()

    private val _navigationFlow = MutableSharedFlow<LoginNavigationEvent>()
    val navigationFlow: SharedFlow<LoginNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> login(email = event.email, password = event.password)

            is LoginEvent.LoginWithRememberMe -> loginWithRememberMe(email = event.email, password = event.password)

            is LoginEvent.Register -> register()
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(LoginRequest(email = email, password = password)).collect() {
                when (it) {
                    is Resource.Success -> {
                        _loginFlow.emit(Resource.Success(data = it.data))
                        _navigationFlow.emit(LoginNavigationEvent.NavigateToHome)
                    }

                    is Resource.Error -> _loginFlow.emit(Resource.Error(errorMessage = it.errorMessage))
                }
            }
        }
    }

    private fun loginWithRememberMe(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(LoginRequest(email = email, password = password)).collect() {
                when (it) {
                    is Resource.Success -> {
                        userDataStoreRepository.saveEmailAndToken(email = email, token = it.data.token)
                        _loginFlow.emit(Resource.Success(data = it.data))
                        _navigationFlow.emit(LoginNavigationEvent.NavigateToHome)
                    }

                    is Resource.Error -> _loginFlow.emit(Resource.Error(errorMessage = it.errorMessage))
                }
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            _navigationFlow.emit(LoginNavigationEvent.NavigateToRegister)
        }
    }

    suspend fun isSessionSaved(){
        viewModelScope.launch {
            val userToken = userDataStoreRepository.readToken().firstOrNull() ?: ""
            if (userToken.isNotBlank()){
                _navigationFlow.emit(LoginNavigationEvent.NavigateToHome)
            }
        }
    }
}

sealed class LoginNavigationEvent {
    data object NavigateToHome : LoginNavigationEvent()
    data object NavigateToRegister : LoginNavigationEvent()
}

