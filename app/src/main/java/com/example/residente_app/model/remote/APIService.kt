package com.example.residente_app.model.remote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class MessageResponse(
    val message:String
)

data class LoginRequest(val username:String, val password: String);
data class TokenResponse(val access:String,val refresh:String);
data class RefreshRequest(val refresh:String);

interface APIService {
    @POST("register")
    suspend fun loginUser(): MessageResponse

    @POST("auth/login/")
    suspend fun login(@Body body: LoginRequest): Response<TokenResponse>

    @POST("auth/logout")
    suspend fun  logout(@Body body:RefreshRequest)
}