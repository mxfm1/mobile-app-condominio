package com.example.residente_app.model.remote.repository

import com.example.residente_app.data.remote.DTO.CreateUserResponse
import com.example.residente_app.data.remote.DTO.GetUsersResponse
import com.example.residente_app.model.remote.interfaces.UserAppAPInterface
import com.example.residente_app.viewmodel.CreateUserForm
import retrofit2.Response
import android.content.Context
import android.util.Log
import com.example.residente_app.data.remote.DTO.AppUsers
import com.example.residente_app.model.remote.RetrofitInstance
import com.example.residente_app.model.remote.RetrofitProviders

class UserAppRepository(context:Context){
    private val api = RetrofitProviders.provideUserAppAPI(context)

    suspend fun createUser(body: CreateUserForm): Response<CreateUserResponse>{
        val response = api.createUser(body)
        Log.d("RESPONSE","respuesta ${response}")
        return  response
    }

    suspend fun getUsers(): Response<List<AppUsers>>{
        val response = api.getUsers()
        Log.d("RESPONSE GET","respuesta ${response}")
        return response
    }
}