package com.example.residente_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.residente_app.model.local.User
import com.example.residente_app.model.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginFormState (
    val username:String = "",
    val password:String = "",
)

sealed class LoginState{
    object Idle: LoginState()
    object Loading: LoginState()
    data class Success(val message:String): LoginState()
    data class Error(val message:String): LoginState()
}

class LoginViewModel(private val repo: AuthRepository):ViewModel(){
    private val _form = MutableStateFlow(LoginFormState())
    val form: StateFlow<LoginFormState> = _form.asStateFlow()

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onUsernameChange(v:String) = _form.update { it.copy(username = v)}
    fun onPasswordChange(v:String) = _form.update { it.copy(password = v) }

    fun limpiarFormulario() = run { _form.value = LoginFormState() }

    fun login(){
        val f = _form.value
        if (f.username.isBlank() || f.password.isBlank()) {
            _state.value = LoginState.Error("Completa todos los campos")
            return
        }

        _state.value = LoginState.Loading

        viewModelScope.launch {
            val user = repo.login(f.username,f.password)
            if(user != null){
                _state.value = LoginState.Success("Bienvenido ${user.username}")
            }else{
                _state.value = LoginState.Error("Usuario o contraseña incorrectos")
            }
        }
    }

    fun seedAdminUser(){
        viewModelScope.launch {
            val adminUser = User(
                username = "admin1",
                password = "admin123",
                role = "admin"
            )
            repo.insertAdminUser(adminUser)
        }
    }
}