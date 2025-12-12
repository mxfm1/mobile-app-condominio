package com.example.residente_app.model.remote
import com.example.residente_app.model.remote.RemoteEntities.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import com.example.residente_app.data.remote.DTO.LoginRequest
import com.example.residente_app.data.remote.DTO.LoginResponse
import com.example.residente_app.data.remote.DTO.LogoutRequest
import com.example.residente_app.data.remote.DTO.LogoutResponse

data class MessageResponse(
    val message:String
)

interface APIService {
    @POST("auth/login/")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>

    @POST("auth/logout/")
    suspend fun  logout(@Body body:LogoutRequest): Response<LogoutResponse>

}