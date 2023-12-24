package com.example.homework17revised2.presentation.login

sealed class LoginEvent {
    data class Login(val email : String, val password : String) : LoginEvent()
    data object Register : LoginEvent()
}