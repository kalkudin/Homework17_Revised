package com.example.homework17revised2.presentation.home

sealed class HomeEvent {
    data object Logout : HomeEvent()
}