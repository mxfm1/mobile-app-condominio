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

}