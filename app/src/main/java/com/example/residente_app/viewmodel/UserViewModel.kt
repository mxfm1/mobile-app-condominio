package com.example.residente_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.residente_app.data.dataStore.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.residente_app.model.remote.repository.UserRepository
import android.content.Context
import com.example.residente_app.data.store.TokenStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

sealed interface ApiLoginState {
    object Idle : ApiLoginState
    object Loading : ApiLoginState
    data class Success(val message: String) : ApiLoginState
    data class Error(val error: String) : ApiLoginState
}

sealed class APILogoutState(
    val message:String? = null,
    val error:String? = null
){
    object Idle: APILogoutState()
    object Loading: APILogoutState()
    class Success(message: String) : APILogoutState(message = message)
    class Error(error:String) : APILogoutState(error = error)
}

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(application.applicationContext)

    private val _loginState = MutableStateFlow<ApiLoginState>(ApiLoginState.Idle)
    private val _logoutState = MutableStateFlow<APILogoutState>(APILogoutState.Idle)
    val loginState = _loginState.asStateFlow()
    val logoutState = _logoutState.asStateFlow()
    val tokenStore = TokenStore(application.applicationContext)

    val accessToken = tokenStore.accessToken
    val isStaff = tokenStore.isStaff
    val isAdmin = tokenStore.isSuperuser

    val sessionLoaded = MutableStateFlow(false)
    init {
        viewModelScope.launch {
            tokenStore.accessToken.collect{
                sessionLoaded.value = true
            }
        }
    }

    fun loginUser(username:String,password:String){
        viewModelScope.launch {
            try{
                _loginState.value = ApiLoginState.Loading;

                val response = repository.login(username,password)
                if(response.isSuccessful){
                    val data = response.body()!!
                    _loginState.value = ApiLoginState.Success(
                        "Bienvenido ${data.user.username}"
                    )
                }else{
                    _loginState.value = ApiLoginState.Error(
                        response.errorBody()?.string() ?: "Error desconocido"
                    )
                }
            }catch(e: Exception){
                _loginState.value = ApiLoginState.Error("Error: ${e.localizedMessage}")
            }
        }
    }

    fun logoutUser(){
        viewModelScope.launch {
            try{
                _logoutState.value = APILogoutState.Loading
                val response = repository.logout()
                _logoutState.value = APILogoutState.Success(response)
            }catch(e: Exception) {

            }
        }
    }
    fun resetState() {
        _loginState.value = ApiLoginState.Idle
    }
}
