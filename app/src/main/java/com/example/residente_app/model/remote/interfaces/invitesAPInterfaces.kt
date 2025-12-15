package com.example.residente_app.model.remote.interfaces

import com.example.residente_app.data.remote.DTO.InviteCreationRequest
import com.example.residente_app.data.remote.DTO.InviteCreationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InvitesAPInterface{
    @POST("invites/create")
    suspend fun createInvitation(@Body body: InviteCreationRequest): Response<InviteCreationResponse>

    @GET("invites/user/all/")
    suspend fun getUserInvitations(): Response<List<InviteCreationResponse>>
}