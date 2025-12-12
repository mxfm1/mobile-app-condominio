package com.example.residente_app.model.remote.repository

import android.content.Context
import com.example.residente_app.data.remote.DTO.AddResidenceResponse
import com.example.residente_app.data.remote.DTO.AddUserResidenceRequest
import com.example.residente_app.data.remote.DTO.AssignOwnerResponse
import com.example.residente_app.data.remote.DTO.AssignResidentResponse
import com.example.residente_app.data.remote.DTO.AssignResidentToHouseRequest
import com.example.residente_app.data.remote.DTO.EditResidentRequest
import com.example.residente_app.data.remote.DTO.ResidenceResponse
import com.example.residente_app.model.remote.RetrofitProviders
import retrofit2.Response

class ResidenceRepository(context: Context) {
    private val api = RetrofitProviders.provideResidenceAPI(context)

    suspend fun houses(): Response<List<ResidenceResponse>>{
        val response = api.getHouses()
        return response
    }

    suspend fun registerUser(identifier:String,userId:Int):Response<AddResidenceResponse>{
        val response = api.addResidentToHouse(identifier, AddUserResidenceRequest(userId))
        return response
    }

    suspend fun getResidence(identifier:String):Response<ResidenceResponse>{
        val response = api.getHouse(identifier)
        return response
    }

    suspend fun assignResidents(identifier:String,residents:List<Int>): Response<AssignResidentResponse>{
        val users = AssignResidentToHouseRequest(user_ids = residents)
        val response = api.assignResident(identifier=identifier, request = users)
        return response
    }

    suspend fun editResidents(identifier:String,modifiedResidents:List<Int>):Response<AssignOwnerResponse>{
        val residents = EditResidentRequest(resident_ids = modifiedResidents)
        val response = api.editResidents(identifier,residents)
        return response
    }
}
