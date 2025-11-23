package com.example.residente_app.model.remote.repository

import android.content.Context
import com.example.residente_app.data.store.TokenStore
import com.example.residente_app.model.remote.RetrofitInstance
import com.example.residente_app.data.remote.DTO.LoginRequest
import com.example.residente_app.data.remote.DTO.LoginResponse
import com.example.residente_app.data.remote.DTO.LogoutRequest
import com.example.residente_app.model.remote.APIService
import kotlinx.coroutines.flow.first
import retrofit2.Response

class UserRepository(context: Context) {

    private val api = RetrofitInstance.create(context);
    private val tokenStore = TokenStore(context);

    suspend fun login(username: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(username, password)
        val response = api.login(request)

        if (response.isSuccessful) {
            response.body()?.also { body ->
                tokenStore.saveTokens(body.access, body.refresh)
                tokenStore.saveSession(body.user)
            }
        }
        return response
    }
    suspend fun logout(): String {
        val refresh = tokenStore.refreshToken.first()

        return if (refresh != null) {
            val request = LogoutRequest(refresh)
            val response = api.logout(request)

            tokenStore.clear()
            response.body()?.message ?: "Sesión cerrada"
        } else {
            "Problemas al cerrar sesión"
        }
    }

    suspend fun getStoredAccessToken() = tokenStore.accessToken.first()
    suspend fun getStoredRefreshToken() = tokenStore.refreshToken.first()
}