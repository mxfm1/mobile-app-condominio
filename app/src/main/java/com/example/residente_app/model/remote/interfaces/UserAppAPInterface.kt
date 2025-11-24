package com.example.residente_app.model.remote.interfaces

import com.example.residente_app.data.remote.DTO.AppUsers
import com.example.residente_app.data.remote.DTO.CreateUserResponse
import com.example.residente_app.data.remote.DTO.GetUsersResponse
import com.example.residente_app.viewmodel.CreateUserForm
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserAppAPInterface {
    @POST("auth/register/")
    suspend fun createUser(@Body body: CreateUserForm): Response<CreateUserResponse>
    @GET("auth/users/")
    suspend fun getUsers(): Response<List<AppUsers>>
}