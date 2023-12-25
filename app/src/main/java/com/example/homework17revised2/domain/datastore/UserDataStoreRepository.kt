package com.example.homework17revised2.domain.datastore

import kotlinx.coroutines.flow.Flow

interface UserDataStoreRepository {

    suspend fun saveEmailAndToken(email: String, token: String)

    fun readEmail() : Flow<String?>

    fun readToken() : Flow<String?>

    suspend fun clear()
}