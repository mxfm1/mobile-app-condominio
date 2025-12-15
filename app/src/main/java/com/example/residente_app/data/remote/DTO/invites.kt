package com.example.residente_app.data.remote.DTO

data class InviteCreationRequest(
    val guest_name:String,
    val reason:String,
    val valid_until:String,
    val aditional_information:String,
    val residence:String,
)

data class InviteCreationResponse(
    val id:Int,
    val code:String,
    val guest_name:String,
    val reason: String,
    val valid_until:String?,
    val aditional_information:String,
    val created_at: String,
    val residence: String,
    val host:Int
)