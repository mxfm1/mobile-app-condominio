package com.example.residente_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.residente_app.data.remote.DTO.AppUsers
import com.example.residente_app.data.remote.DTO.GetUsersResponse
import com.example.residente_app.model.remote.repository.UserAppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CreateUserForm(
    val username:String = "",
    val password:String = "",
    val password2:String = "",
    val email:String = "",
    val first_name:String = "",
    val last_name:String = "",
)

sealed class CreateUserState(){
    object Idle: CreateUserState()
    object Loading: CreateUserState()
    class Success(): CreateUserState()
    class Error(): CreateUserState()
}

sealed class GetUsersState(
    message:String?=null
){
    object Idle: GetUsersState()
    object Loading: GetUsersState()
    class Success(val users: List<AppUsers>): GetUsersState()
    class Error(val message:String): GetUsersState(message)
}


class UsersAppViewModel(application: Application)   : AndroidViewModel(application){

    private val _form = MutableStateFlow(CreateUserForm())
    val form = _form.asStateFlow()

    private val _state = MutableStateFlow<CreateUserState>(CreateUserState.Idle)
    val state = _state.asStateFlow()

    private val _getUsersState = MutableStateFlow<GetUsersState>(GetUsersState.Idle)
    val getUsersState = _getUsersState.asStateFlow()
    private val repository = UserAppRepository(application)

    fun createUser(body: CreateUserForm){
        viewModelScope.launch {

            _state.value = CreateUserState.Loading
            try{
                val response = repository.createUser(body)
                if(response.isSuccessful){
                    _state.value = CreateUserState.Success()
                }else{
                    _state.value = CreateUserState.Error()
                }
            }catch(e: Exception){
                _state.value = CreateUserState.Error()
            }
        }
    }

    fun getUsers(){
        viewModelScope.launch {
            _getUsersState.value = GetUsersState.Loading
            try {
                val response = repository.getUsers()
                Log.d("FROM VIEWMODEL METHOD", "${response}")

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _getUsersState.value = GetUsersState.Success(body)
                    } else {
                        _getUsersState.value = GetUsersState.Error("La respuesta llegó vacía (null).")
                    }
                } else {
                    _getUsersState.value = GetUsersState.Error("Código HTTP: ${response.code()}")
                }

            } catch (e: Exception) {
                _getUsersState.value = GetUsersState.Error("Excepción: ${e.message}")
            }
        }
    }

    fun updateUsername(username:String){
        _form.value = _form.value.copy(username = username)
    }

    fun updateField(key:String,value:String){
        _form.value = when(key){
            "username" -> _form.value.copy(username = value)
            "email" -> _form.value.copy(email = value)
            "first_name" -> _form.value.copy(first_name = value)
            "last_name" -> _form.value.copy(last_name = value)
            "password" -> _form.value.copy(password = value)
            "password2" -> _form.value.copy(password2 = value)
            else -> _form.value
        }
    }
}