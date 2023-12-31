package com.example.homework17revised2.data.resource

sealed class Resource <T> {
    data class Success<T>(val data : T) : Resource<T>()
    data class Error<T>(val errorMessage : String) : Resource<T>()
}
