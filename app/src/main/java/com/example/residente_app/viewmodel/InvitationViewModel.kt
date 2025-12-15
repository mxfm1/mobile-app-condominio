package com.example.residente_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.residente_app.data.remote.DTO.InviteCreationRequest
import com.example.residente_app.data.remote.DTO.InviteCreationResponse
import com.example.residente_app.model.remote.repository.InvitationsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class GetUserInvitationsState{
    object Idle: GetUserInvitationsState()
    object Loading: GetUserInvitationsState()
    class Success(): GetUserInvitationsState()
    class Error(val message:String = "Error al cargar las invitaciones"): GetUserInvitationsState()
}
sealed class CreateInvitationState{
    object Idle: CreateInvitationState()
    object Loading: CreateInvitationState()
    class Success(val message:String = "Invitación creada con éxito"): CreateInvitationState()
    class Error(val message:String? = "Hubo un error al crear la invitación.. Porfavor intenta más tarde"): CreateInvitationState()
}

class InvitationViewModel(application: Application): AndroidViewModel(application){

    private val repository = InvitationsRepository(application)
    private val _createInviteState = MutableStateFlow<CreateInvitationState>(CreateInvitationState.Idle)
    val createInviteState = _createInviteState.asStateFlow()

    private val _userInvitationsList = MutableStateFlow<List<InviteCreationResponse>>(emptyList())
    val userInvitationsList = _userInvitationsList.asStateFlow()

    private val _getUserInvitationState = MutableStateFlow<GetUserInvitationsState>(GetUserInvitationsState.Idle)
    val getUserInvitationsState = _getUserInvitationState.asStateFlow()
    private val _invitation = MutableStateFlow<InviteCreationResponse?>(null)
    val invitation = _invitation.asStateFlow()



    fun createInvitation(body: InviteCreationRequest){
        viewModelScope.launch {
            _createInviteState.value = CreateInvitationState.Loading
            try{
                val response = repository.createInvitation(body)
                if(response.isSuccessful()){
                    val invite = response.body()
                    if(invite != null){
                        _invitation.value = invite
                        _createInviteState.value = CreateInvitationState.Success()
                    }else{
                        _createInviteState.value = CreateInvitationState.Error("Respuesta vacía..")
                    }
                }else{
                    _createInviteState.value = CreateInvitationState.Error("Error: HTTP${response.code()} ${response.errorBody()}")
                }
            }catch (e: Exception){
                _createInviteState.value = CreateInvitationState.Error()
            }
        }
    }

    fun getUserInvitations(){
        viewModelScope.launch {
            _getUserInvitationState.value = GetUserInvitationsState.Loading
            try{
                val response = repository.getUserInvitations()
                if(response.isSuccessful){
                    val data = response.body()
                    if(data != null){
                        _userInvitationsList.value = data
                        _getUserInvitationState.value = GetUserInvitationsState.Success()
                    }else{
                        _getUserInvitationState.value = GetUserInvitationsState.Error("Error obteniendo las inviotaciones: HTTP: ${response.code()} ${response.errorBody()}")
                    }
                }
            }catch(e:Exception){
                _getUserInvitationState.value = GetUserInvitationsState.Error()
            }
        }
    }

    fun clearInvitation(){
        _invitation.value = null
        _createInviteState.value = CreateInvitationState.Idle
    }

    fun resetInvitationCreationState(){
        _createInviteState.value = CreateInvitationState.Idle
    }
}