package com.example.residente_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.residente_app.data.dataStore.SessionManager
import com.example.residente_app.model.remote.repository.UserRestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log
import com.example.residente_app.model.remote.RefreshRequest

sealed interface ApiLoginState {
    object Idle : ApiLoginState
    object Loading : ApiLoginState
    data class Success(val message: String) : ApiLoginState
    data class Error(val error: String) : ApiLoginState
}

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRestRepository()
    private val tokenStore = SessionManager(application)

    private val _loginResult = MutableStateFlow<ApiLoginState>(ApiLoginState.Idle)
    val loginResult = _loginResult.asStateFlow()

    fun loginUserFromRESTAPI(username: String, password: String) {
        Log.d("API_LOGIN", "Username: $username, Password: $password")
        viewModelScope.launch {
            try {
                _loginResult.value = ApiLoginState.Loading
                Log.d("API_LOGIN", "Username: $username, Password: $password")
                val response = repository.login(username, password)
                if (response.isSuccessful) {
                    val tokens = response.body()!!

                    tokenStore.saveTokens(
                        access = tokens.access,
                        refresh = tokens.refresh
                    )

                    _loginResult.value = ApiLoginState.Success("Login exitoso")

                } else {
                    val err = response.errorBody()?.string() ?: "Error desconocido"
                    _loginResult.value = ApiLoginState.Error(err)
                }

            } catch (e: Exception) {
                _loginResult.value = ApiLoginState.Error(e.localizedMessage ?: "Error desconocido")
            }
        }
    }

    fun LogoutUserFromAPIRest(refresh:RefreshRequest){
       viewModelScope.launch {
           try {
               _loginResult.value = ApiLoginState.Loading
               val response = repository.logout(refresh)
               tokenStore.clearTokens()
               _loginResult.value = ApiLoginState.Success("Sesion cerrada con éxito")
           }catch(e: Exception){
               _loginResult.value = ApiLoginState.Error("No se pudo cerrar sesion")
           }
       }
    }

    fun resetState() {
        _loginResult.value = ApiLoginState.Idle
    }

    fun getAccessToken() = tokenStore.accessToken
    fun getRefreshToken() = tokenStore.refreshToken
}
