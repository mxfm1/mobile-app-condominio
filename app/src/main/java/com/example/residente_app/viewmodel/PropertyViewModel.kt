package com.example.residente_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PropertyAddForm(
    val identifier:String = ""
)

sealed class CreatePropertyState(
){
    object Idle: CreatePropertyState()
    object Loading: CreatePropertyState()
    class Success(): CreatePropertyState()
    class Error(): CreatePropertyState()
}

class PropertyViewModel: ViewModel(){
    private val _form = MutableStateFlow(PropertyAddForm())
    private val form = _form.asStateFlow()

    fun updateIdentifier(idx:String)= _form.update { it.copy(idx)}

    fun clearIdentifierForm() = run{_form.value = PropertyAddForm()}


    init{
        viewModelScope.launch {  }
    }
}