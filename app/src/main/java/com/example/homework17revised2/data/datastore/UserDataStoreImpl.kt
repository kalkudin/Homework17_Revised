package com.example.homework17revised2.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.homework17revised2.domain.datastore.UserDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataStoreImpl @Inject constructor (private val dataStore: DataStore<Preferences>) : UserDataStoreRepository {

    override suspend fun saveEmailAndToken(email: String, token: String) {
        dataStore.edit { preferences ->
            Log.d("DataStoreRepository", "saved email and token")
            preferences[PreferencesKey.EMAIL] = email
            preferences[PreferencesKey.TOKEN] = token
        }
    }

    override fun readEmail(): Flow<String> {
        return dataStore.data.map { preferences ->
            Log.d("DataStoreRepository", "read email")
            preferences[PreferencesKey.EMAIL] ?: ""
        }
    }

    override fun readToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            Log.d("DataStoreRepository", "read token")
            preferences[PreferencesKey.TOKEN] ?: ""
        }
    }


    override suspend fun clear() {
        dataStore.edit { preferences ->
            Log.d("DataStoreRepository", "data store cleared")
            preferences.clear()
        }
    }

    private object PreferencesKey {
        val EMAIL = stringPreferencesKey("email")
        val TOKEN = stringPreferencesKey("token")
    }
}