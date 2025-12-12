package com.example.residente_app.data.remote.DTO

data class ResidenceResponse(
    val identifier:String,
    val owner: ownerObject?,
    val created_by:Int,
    val created_at:String,
    val updated_at:String,
    val residents: List<AppUsers>
)

data class AddUserResidenceRequest(
    val user_id: Int
)

data class AssignResidentToHouseRequest(
    val user_ids: List<Int>
)

//asignar propietario

data class ownerObject(
    val id:Int,
    val username:String,
    val email:String,
    val first_name:String,
    val last_name:String
)
data class AssignOwnerToHouse(
    val new_owner_id: Int
);
data class AssignOwnerResponse(
    val identifier: String,
    val owner: ownerObject?,
    val created_by: Int,
    val created_at: String,
    val updated_at: String,
    val residents: List<AppUsers>
)

//Editar residentes
data class EditResidentRequest(
    val resident_ids: List<Int>
)

data class AssignResidentResponse(
    val message:String,
    val assigned: List<Int>,
    val not_found: List<Int>,
    val residence: String
)

data class AddResidenceResponse(
    val message: String,
    val user_id:Int,
    val residence:String
)
