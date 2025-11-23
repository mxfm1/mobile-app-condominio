package com.example.residente_app.data.store

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.residente_app.data.remote.DTO.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class TokenStore(private val context: Context){

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val USER_ID = intPreferencesKey("user_id")
        private val USER_USERNAME = stringPreferencesKey("user_username")
        private val USER_EMAIL = stringPreferencesKey("user_email")

        private val USER_ISSUPERUSER = booleanPreferencesKey("user_issuperuser")
        private val USER_ISSTAFF = booleanPreferencesKey("user_isstaff")
    }

    private fun getAccessToken() {

    }

    val accessToken: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[ACCESS_TOKEN]
    }

    val refreshToken: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[REFRESH_TOKEN]
    }

    val userId: Flow<Int?> = context.dataStore.data.map { it[USER_ID] }
    val username: Flow<String?> = context.dataStore.data.map { it[USER_USERNAME] }
    val email: Flow<String?> = context.dataStore.data.map { it[USER_EMAIL] }

    val isSuperuser: Flow<Boolean> = context.dataStore.data.map { it[USER_ISSTAFF] ?: false }
    val isStaff: Flow<Boolean> = context.dataStore.data.map { it[USER_ISSTAFF] ?: false }
    suspend fun saveTokens(access:String,refresh:String){
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = access
            preferences[REFRESH_TOKEN] = refresh
        }
    }

    suspend fun saveSession(user: UserDto){
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = user.id
            prefs[USER_USERNAME] = user.username
            prefs[USER_EMAIL] = user.email

            prefs[USER_ISSUPERUSER] = user.is_superuser
            prefs[USER_ISSTAFF] = user.is_staff
        }
    }

    suspend fun clear(){
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}