package com.example.residente_app.model.remote.interfaces

import com.example.residente_app.data.remote.DTO.AddResidenceResponse
import com.example.residente_app.data.remote.DTO.AddUserResidenceRequest
import com.example.residente_app.data.remote.DTO.AppUsers
import com.example.residente_app.data.remote.DTO.AssignOwnerResponse
import com.example.residente_app.data.remote.DTO.AssignOwnerToHouse
import com.example.residente_app.data.remote.DTO.AssignResidentResponse
import com.example.residente_app.data.remote.DTO.AssignResidentToHouseRequest
import com.example.residente_app.data.remote.DTO.CreateUserResponse
import com.example.residente_app.data.remote.DTO.EditResidentRequest
import com.example.residente_app.data.remote.DTO.GetUsersResponse
import com.example.residente_app.data.remote.DTO.Residence
import com.example.residente_app.data.remote.DTO.ResidenceResponse
import com.example.residente_app.viewmodel.CreateUserForm
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAppAPInterface {
    @POST("auth/register/")
    suspend fun createUser(@Body body: CreateUserForm): Response<CreateUserResponse>
    @GET("auth/users/")
    suspend fun getUsers(): Response<List<AppUsers>>

    @GET("auth/users/{id}/")
    suspend fun  getUser(@Path("id")id:Int):Response<AppUsers>
}

interface ResidenceAPInterface{
    @GET("houses/")
    suspend fun getHouses(): Response<List<ResidenceResponse>>

    @POST("houses/{identifier}/add-resident")
    suspend fun addResidentToHouse(
        @Path("identifier") identifier:String,
        @Body request: AddUserResidenceRequest
    ):Response<AddResidenceResponse>

    @GET("houses/{identifier}/")
    suspend fun getHouse(
        @Path(value = "identifier") identifier:String
    ):Response<ResidenceResponse>

    @POST("houses/{identifier}/add-residents")
    suspend fun assignResident(
        @Path("identifier") identifier:String,
        @Body request: AssignResidentToHouseRequest
    ): Response<AssignResidentResponse>

    //asignar propietario
    @PUT("houses/{identifier}/")
    suspend fun assignOwner(
        @Path("identifier") identifier: String,
        @Body request: AssignOwnerToHouse
    ): Response<AssignOwnerResponse>

    @PUT("houses/{identifier}/")
    suspend fun editResidents(
        @Path("identifier") identifier: String,
        @Body request: EditResidentRequest
    ): Response<AssignOwnerResponse>
}