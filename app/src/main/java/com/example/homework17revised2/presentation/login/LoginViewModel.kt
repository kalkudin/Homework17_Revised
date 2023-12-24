package com.example.homework17revised2.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework17revised2.common.Resource
import com.example.homework17revised2.domain.login.LoginRepository
import com.example.homework17revised2.domain.login.LoginRequest
import com.example.homework17revised2.domain.login.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository : LoginRepository) : ViewModel(){
    private val _loginFlow = MutableSharedFlow<Resource<LoginResponse>>()
    val loginFlow: SharedFlow<Resource<LoginResponse>> get() = _loginFlow.asSharedFlow()

    private val _navigationFlow = MutableSharedFlow<NavigationEvent>()
    val navigationFlow : SharedFlow<NavigationEvent> get() = _navigationFlow.asSharedFlow()

    fun onEvent(event : LoginEvent){
        when(event){
            is LoginEvent.Login -> login(email = event.email, password = event.password )
            is LoginEvent.Register -> register()
        }
    }

    private fun login(email : String, password : String){
        viewModelScope.launch {
            loginRepository.login(LoginRequest(email = email, password = password)).collect(){
                when(it){
                    is Resource.Success -> {
                        _loginFlow.emit(Resource.Success(data = it.data))
                        delay(1000)
                        _navigationFlow.emit(NavigationEvent.NavigateToHome)
                    }
                    is Resource.Error -> _loginFlow.emit(Resource.Error(errorMessage = it.errorMessage))
                }
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            _navigationFlow.emit(NavigationEvent.NavigateToRegister)
        }
    }
}

sealed class NavigationEvent{
    data object NavigateToHome : NavigationEvent()
    data object NavigateToRegister : NavigationEvent()
}