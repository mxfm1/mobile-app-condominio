package com.example.residente_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.residente_app.data.remote.DTO.CreateResidenceRequest
import com.example.residente_app.data.remote.DTO.CreateResidenceResponse
import com.example.residente_app.data.remote.DTO.Residence
import com.example.residente_app.data.remote.DTO.ResidenceResponse
import com.example.residente_app.model.remote.repository.ResidenceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class ResidenceCreateState{
    object Idle: ResidenceCreateState()
    object Loading: ResidenceCreateState()
    class Success(): ResidenceCreateState()
    class Error(): ResidenceCreateState()
}

sealed class ResidenceGetState{
    object Idle: ResidenceGetState()
    object Loading: ResidenceGetState()
    class Success(val residence: List<ResidenceResponse>): ResidenceGetState()
    class Error(): ResidenceGetState()
}


sealed class getResidenceState{
    object Idle: getResidenceState()
    object Loading: getResidenceState()
    class Success(): getResidenceState()
    data class Error(val error:String): getResidenceState()
}

// ---------------------------
//    ASIGNACION DE USUARIOS
// --------------------------

sealed class AssignUserState{
    object Idle: AssignUserState()
    object Loading: AssignUserState()
    data class Success(val message:String): AssignUserState()
    data class Error(val message:String="Hubo un error al asignar el usuarios"): AssignUserState()
}

sealed class UpdateResidentsState(){
    object Idle: UpdateResidentsState()
    object Loading: UpdateResidentsState()
    data class Success(val message:String):UpdateResidentsState()
    data class Error(val message:String="Hubo un error al asignar los residentes"):UpdateResidentsState()
}

sealed class CreateResidenceState(){
    object Idle: CreateResidenceState()
    object Loading: CreateResidenceState()
    data class Success(val message:String = "Residence Creada con éxito"): CreateResidenceState()
    data class Error(val message:String="Error al crear la residencia"): CreateResidenceState()
}

class ResidenceViewModel(application: Application): AndroidViewModel(application){

    private val _createResidenceState = MutableStateFlow<CreateResidenceState>(CreateResidenceState.Idle)
    val createResidenceState = _createResidenceState.asStateFlow()
    private val _createState = MutableStateFlow<ResidenceCreateState>(ResidenceCreateState.Idle)
    val createState = _createState.asStateFlow()

    private val _getState = MutableStateFlow<ResidenceGetState>(ResidenceGetState.Idle)
    val getState = _getState.asStateFlow()

    private val _houses = MutableStateFlow<List<ResidenceResponse>>(emptyList<ResidenceResponse>());
    val houses = _houses.asStateFlow()

    //OBTENER 1 RESIDENCIA
    private val _stateGetResidence = MutableStateFlow<getResidenceState>(getResidenceState.Idle)
    val stateGetResidence = _stateGetResidence.asStateFlow()
    //variable alamcenadora de la residencia
    private val _residence = MutableStateFlow<ResidenceResponse?>(null)
    val residence = _residence.asStateFlow()
    val repository = ResidenceRepository(application)

    //asignar usuariok sa la residencia
    private val _assignUserState =
        MutableStateFlow<AssignUserState>(AssignUserState.Idle)
    val assignUserState = _assignUserState.asStateFlow()

    //modificar residentes

    private val _updateResidentsState = MutableStateFlow<UpdateResidentsState>(UpdateResidentsState.Idle)
    val updateResidentsState = _updateResidentsState.asStateFlow()

    private val _currentResidents = MutableStateFlow<List<Int>>(emptyList());
    val currentResidents = _currentResidents.asStateFlow()

    fun getHouses(){
        viewModelScope.launch {
            _getState.value  = ResidenceGetState.Loading
            try{
                val response = repository.houses()
                if(response.isSuccessful){
                    _getState.value = ResidenceGetState.Success(residence = response.body()!!)
                    _houses.value = response.body()!!;
                }else{
                    _getState.value = ResidenceGetState.Error()
                }
            }catch(e: Exception){
                _getState.value = ResidenceGetState.Error()
            }
        }
    }

    fun getHouse(identifier:String){
        viewModelScope.launch {
            _stateGetResidence.value = getResidenceState.Loading
            try{
                val response = repository.getResidence(identifier)
                if(response.isSuccessful && response.body() != null){
                    _stateGetResidence.value = getResidenceState.Success()
                    _residence.value = response.body()
                    _currentResidents.value = response.body()!!.residents.map { it.id }

                }else{
                    _stateGetResidence.value = getResidenceState.Error("Respuesta vacía del servidor")
                }
            }catch(e: Exception){
                _stateGetResidence.value  = getResidenceState.Error("Hubo un error obteniendo la residencia: $e")
            }
        }
    }

    fun createResidence(identifier:String){
        viewModelScope.launch {
            _createResidenceState.value = CreateResidenceState.Loading
            try{
                val request = CreateResidenceRequest(identifier = identifier)
                val response = repository.createResidence(request)
                if(response.isSuccessful()){
                   val res = response.body()
                   _createResidenceState.value = CreateResidenceState.Success()
                }else{
                    _createResidenceState.value = CreateResidenceState.Error("Error al crear la residencia: HTTP: ${response.code()}")
                }
            }catch(e:Exception){
                _createResidenceState.value = CreateResidenceState.Error()
            }
        }
    }

    fun assignResidents(identifier:String,residentsId: List<Int>){
        Log.d("RESPONSE","IDENTIFIER: ${identifier} residents id: ${residentsId}")
       viewModelScope.launch {
           _assignUserState.value = AssignUserState.Loading
           try{
               _assignUserState.value  = AssignUserState.Loading
               val response = repository.assignResidents(identifier,residentsId)
               if(response.isSuccessful){
                   _assignUserState.value = AssignUserState.Success(response.message())
               }else{
                   _assignUserState.value = AssignUserState.Error("Hubo un error al asignar usuarios a las residencias")
               }
           }catch(e: Exception){
               _assignUserState.value = AssignUserState.Error("Hubo un error: $e")
           }
       }
    }

    fun editResidents(identifier: String,residentsIds:List<Int>){
        viewModelScope.launch {

        }
    }

    fun removeResidentFromList(identifier:String,id:Int){
        _currentResidents.value = _currentResidents.value.filter { it != id };
        _updateResidentsState.value = UpdateResidentsState.Loading
        viewModelScope.launch {
            try{
                val response = repository.editResidents(identifier,_currentResidents.value)
                if(response.isSuccessful){
                    _updateResidentsState.value = UpdateResidentsState.Success("Usuario eliminado con éxito")
                }else{
                    _updateResidentsState.value = UpdateResidentsState.Error("No se pudo eliminar el usuario: HTTP:${response.code()} ")
                }
            }catch(e:Exception){
                _updateResidentsState.value = UpdateResidentsState.Error("Hubo un error: ${e}")
            }
        }
    }

    fun resetUpdateState(){
        _updateResidentsState.value = UpdateResidentsState.Idle
    }

    fun resetState(){
        _assignUserState.value = AssignUserState.Idle
    }
}