package com.example.residente_app.model.remote.repository

import com.example.residente_app.model.remote.LoginRequest
import com.example.residente_app.model.remote.MessageResponse
import com.example.residente_app.model.remote.RetrofitInstance
import com.example.residente_app.model.remote.TokenResponse
import retrofit2.Response
import com.example.residente_app.model.remote.RefreshRequest

class UserRestRepository {

    suspend fun loginUser(): MessageResponse{
        return RetrofitInstance.api.loginUser()
    }

    suspend fun login(username:String,password:String): Response<TokenResponse>{
        val request = LoginRequest(
            username = username,
            password = password
        )
        val body = LoginRequest(username,password)
        return RetrofitInstance.api.login(body)
    }

    suspend fun logout(refresh:RefreshRequest){
        return RetrofitInstance.api.logout(refresh)
    }
}