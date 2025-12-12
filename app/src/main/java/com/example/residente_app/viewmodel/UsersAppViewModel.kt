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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class CreateUserForm(
    val username:String = "",
    val password:String = "",
    val password2:String = "",
    val email:String = "",
    val first_name:String = "",
    val last_name:String = "",
)

sealed class CreateUserState(
    val message:String? = "",
){
    object Idle: CreateUserState()
    object Loading: CreateUserState()
    class Success(message:String): CreateUserState(message)
    class Error(message:String): CreateUserState()
    data class FieldErrors(val errors:UserRegisterError): CreateUserState()
}

sealed class GetUsersState(
    message:String?=null
){
    object Idle: GetUsersState()
    object Loading: GetUsersState()
    class Success(val users: List<AppUsers>): GetUsersState()
    class Error(val message:String): GetUsersState(message)
}

sealed class GetUserDataState(){
    object Idle: GetUserDataState()
    object Loading: GetUserDataState()
    class Success(val user: AppUsers): GetUserDataState()
    class Error(val message:String): GetUserDataState()
}

//Errores al registrar un usuario
data class UserRegisterError(
    val username:List<String>?=null,
    val password:List<String>?= null,
    val password2:List<String>?= null,
    val email:List<String>? = null,
    val first_name:List<String>?= null,
    val last_name:List<String>?= null
)

class UsersAppViewModel(application: Application)   : AndroidViewModel(application){

    private val _getUserState = MutableStateFlow<GetUserDataState>(GetUserDataState.Idle)
    val getUserState = _getUserState.asStateFlow()
    private val _form = MutableStateFlow(CreateUserForm())
    val form = _form.asStateFlow()

    private val _state = MutableStateFlow<CreateUserState>(CreateUserState.Idle)
    val state = _state.asStateFlow()

    private val _getUsersState = MutableStateFlow<GetUsersState>(GetUsersState.Idle)
    val getUsersState = _getUsersState.asStateFlow()

    private val _userList = MutableStateFlow<List<AppUsers>>(emptyList())
    val userList = _userList.asStateFlow()
    private val repository = UserAppRepository(application)

    fun createUser(body: CreateUserForm){
        viewModelScope.launch {

            _state.value = CreateUserState.Loading
            try{
                val response = repository.createUser(body)
                Log.d("RESPONSE","$response")
                if(response.isSuccessful){
                    _state.value = CreateUserState.Success("Usuario creado con exito")
                }else{
                    val errorJson = response.errorBody()?.string()
                    val type = object : TypeToken<UserRegisterError>() {}.type

                    val fieldErrors: UserRegisterError? = try {
                        Gson().fromJson(errorJson, type)
                    } catch (e: Exception) {
                        null
                    }

                    if (fieldErrors != null) {
                        _state.value = CreateUserState.FieldErrors(fieldErrors)
                    } else {
                        _state.value = CreateUserState.Error("Error desconocido al crear usuario.")
                    }
                }
            }catch(e: Exception){
                _state.value = CreateUserState.Error(message = "Error: $e")
            }
        }
    }

    fun getUser(id:Int){
        viewModelScope.launch {
            _getUserState.value = GetUserDataState.Loading

            try{
                val response = repository.getUser(id)
                if(response.isSuccessful){
                    _getUserState.value = GetUserDataState.Success(user=response.body()!!)
                }else{
                    _getUserState.value = GetUserDataState.Error("Error al pedir la info")
                }
            }catch(e: Exception){
                _getUserState.value = GetUserDataState.Error("Error: $e")
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
                        _userList.value = body
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
    fun clearFields(){
        _form.value = CreateUserForm()
    }

    fun clearState(){
        _state.value = CreateUserState.Idle
    }
}