package com.example.homework17revised2.presentation.register

sealed class RegisterEvent {
    data class Register(
        val email : String,
        val password : String,
        val repeatPassword : String
    ): RegisterEvent()
}