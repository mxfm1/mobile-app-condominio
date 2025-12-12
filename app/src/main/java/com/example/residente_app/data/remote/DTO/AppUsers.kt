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

data class Residence(
    val identifier: String,
    val owner: Int?,
    val created_by: Int?,
    val created_at: String,
    val updated_at: String
)

data class AppUsersWithHouses(
    val id: Int,
    val username: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val houses: List<Residence>
)