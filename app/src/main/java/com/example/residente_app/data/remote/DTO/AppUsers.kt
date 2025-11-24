package com.example.residente_app.data.remote.DTO

data class CreateUserResponse(
    val username:String,
    val email:String,
    val first_name:String,
    val last_name:String
)

data class AppUsers(
    val id:Int,
    val username:String,
    val email:String,
    val first_name:String,
    val last_name:String
)
data class GetUsersResponse(
    val userList: List<AppUsers>
)