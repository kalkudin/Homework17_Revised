package com.example.homework17revised2.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework17revised2.data.resource.Resource
import com.example.homework17revised2.domain.register.RegisterRepository
import com.example.homework17revised2.domain.register.model.RegisterRequest
import com.example.homework17revised2.domain.register.model.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor (private val registerRepository: RegisterRepository) : ViewModel() {
    private val _registerFlow = MutableStateFlow<Resource<RegisterResponse>?>(null)
    val registerFlow : StateFlow<Resource<RegisterResponse>?> = _registerFlow.asStateFlow()

    private val _navigationFlow = MutableStateFlow<RegisterNavigationEvent>(RegisterNavigationEvent.None)
    val navigationFlow : StateFlow <RegisterNavigationEvent> = _navigationFlow.asStateFlow()

    fun onEvent(event : RegisterEvent){
        when(event){
            is RegisterEvent.Register -> register(email = event.email, password = event.password, repeatPassword = event.repeatPassword)
        }
    }

    fun register(email : String, password : String, repeatPassword : String){
        viewModelScope.launch {

            if(arePasswordsFilled(firstPassword = password, secondPassword = repeatPassword)){
                _registerFlow.value = Resource.Error(errorMessage = "fill in both passwords")
                return@launch
            }

            if(!arePasswordsTheSame(firstPassword = password, secondPassword = repeatPassword)){
                _registerFlow.value = Resource.Error(errorMessage = "passwords must be the same")
                return@launch
            }

            registerRepository.register(RegisterRequest(email = email, password = password)).collect(){
                when(it){
                    is Resource.Success ->{
                        _registerFlow.value = Resource.Success(data = it.data)
                        _navigationFlow.value = RegisterNavigationEvent.NavigateToLogin
                    }
                    is Resource.Error ->
                        _registerFlow.value = Resource.Error(errorMessage = it.errorMessage)
                }
            }
        }
    }

    private fun arePasswordsTheSame(firstPassword : String, secondPassword : String) : Boolean{
        return firstPassword == secondPassword
    }

    private fun arePasswordsFilled(firstPassword : String, secondPassword : String) : Boolean{
        return firstPassword.isEmpty() || secondPassword.isEmpty()
    }
}

sealed class RegisterNavigationEvent {

    data object NavigateToLogin : RegisterNavigationEvent()

    data object None : RegisterNavigationEvent()
}