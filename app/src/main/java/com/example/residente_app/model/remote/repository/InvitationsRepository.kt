package com.example.residente_app.model.remote.repository

import android.content.Context
import com.example.residente_app.data.remote.DTO.InviteCreationRequest
import com.example.residente_app.data.remote.DTO.InviteCreationResponse
import com.example.residente_app.model.remote.RetrofitProviders
import retrofit2.Response

class InvitationsRepository(context: Context){
    private val api = RetrofitProviders.provideInvitesAPI(context)

    suspend fun createInvitation(body: InviteCreationRequest): Response<InviteCreationResponse>{
        val response = api.createInvitation(body)
        return response
    }

    suspend fun getUserInvitations(): Response<List<InviteCreationResponse>>{
        val response = api.getUserInvitations()
        return response
    }
}