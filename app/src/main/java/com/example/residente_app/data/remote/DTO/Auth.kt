package com.example.residente_app.data.remote.DTO

data class LoginRequest(
    val username:String,
    val password: String,
)

    data class LoginResponse(
    val access:String,
    val refresh:String,
    val user: UserDto
)

data class LogoutResponse(
    val message:String
)
data class UserDto(
    val id: Int,
    val username: String,
    val email: String,
    val is_superuser: Boolean,
    val is_staff: Boolean
)

data class LogoutRequest(
    val refresh:String
)
