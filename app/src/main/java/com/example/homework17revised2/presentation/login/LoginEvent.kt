package com.example.homework17revised2.presentation.login

import android.util.Log

sealed class LoginEvent {
    data class Login(val email : String, val password : String) : LoginEvent()
    data class LoginWithRememberMe(val email : String, val password : String) : LoginEvent()
    data object Register : LoginEvent()
}