package com.example.residente_app.ui.screens.admin.houses

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.font.FontWeight
import com.example.residente_app.data.remote.DTO.AppUsers
import com.example.residente_app.data.remote.DTO.ResidenceResponse
import com.example.residente_app.viewmodel.ResidenceViewModel
import com.example.residente_app.viewmodel.UsersAppViewModel
import com.example.residente_app.viewmodel.getResidenceState
import com.example.residente_app.ui.screens.admin.ProfileField
import com.example.residente_app.ui.theme.AppColors
import com.example.residente_app.util.formatFecha
import com.example.residente_app.viewmodel.AssignUserState
import com.example.residente_app.viewmodel.ResidenceGetState
import com.example.residente_app.viewmodel.UpdateResidentsState
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HouseDetailScreen(
    residenceVm: ResidenceViewModel,
    usersVm: UsersAppViewModel,
    identifier: String,
    onBack: () -> Unit,
    onAssign: (List<Int>) -> Unit
) {
    LaunchedEffect(identifier) {
        residenceVm.getHouse(identifier)
        usersVm.getUsers()
    }

    val residenceState by residenceVm.stateGetResidence.collectAsState()
    val residence by residenceVm.residence.collectAsState()

    val userList by usersVm.userList.collectAsState()
    val selectedUsers = remember { mutableStateListOf<Int>() }
    val asignResidentState by residenceVm.assignUserState.collectAsState()
    var asignResidentError:String? = ""

    val HouseCurrentResidents by residenceVm.currentResidents.collectAsState()
    val updateState by residenceVm.updateResidentsState.collectAsState()

    LaunchedEffect(updateState) {
        when (updateState) {
            is UpdateResidentsState.Success -> {
                residenceVm.getHouse(identifier)   // recargar info correcta de la residencia
                residenceVm.resetUpdateState()  // volver a Idle
            }
            is UpdateResidentsState.Error -> {
                // opcional: mostrar un snackbar / texto
            }
            else -> Unit
        }
    }

    LaunchedEffect(asignResidentState) {
        when(asignResidentState){
            is AssignUserState.Success -> {
                residenceVm.getHouse(identifier)

                selectedUsers.clear()
                residenceVm.resetState()
            }

            is AssignUserState.Error -> {
                asignResidentError = AssignUserState.Error().message

            }
            else -> Unit
        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0F0F))
            .padding(20.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            item {
                // ---------- HEADER ----------
                Row(verticalAlignment = Alignment.CenterVertically) {

                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = "Detalles de residencia",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            when (residenceState) {
                is getResidenceState.Loading -> item {
                    CircularProgressIndicator(color = Color.White)
                }

                is getResidenceState.Success -> item {
                    residence?.let { res ->
                        ResidenceCard(res)

                        Spacer(modifier = Modifier.height(25.dp))

                        Text(
                            text = "Gestionar Residentes",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                is getResidenceState.Error -> item {
                    Text(
                        text = "Error cargando la residencia",
                        color = Color.Red
                    )
                }
                else -> Unit
            }
            // --- LISTA DE RESIDENTES EDITABLES ---
            items(residence?.residents ?: emptyList()) { resident ->
                ResidentEditableItem(
                    resident = resident,
                    onRemove = {
                        residenceVm.removeResidentFromList(
                            residence?.identifier ?: "",
                            resident.id
                        )
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Asignar residentes",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            // --- LISTA DE USUARIOS PARA ASIGNAR ---
            items(userList) { user ->
                UserSelectItem(
                    user = user,
                    isSelected = selectedUsers.contains(user.id),
                    onToggle = { id ->
                        if (selectedUsers.contains(id))
                            selectedUsers.remove(id)
                        else
                            selectedUsers.add(id)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
        AssignButton(
            enabled = selectedUsers.isNotEmpty(),
            onClick = { residenceVm.assignResidents(identifier, selectedUsers) },
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
        )
    }
}

@Composable
fun ResidenceCard(residence: ResidenceResponse) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1C1C1C), RoundedCornerShape(20.dp))
            .padding(20.dp)
    ) {

        // 📌 COLUMNA IZQUIERDA (info de la propiedad)
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Casa: ${residence.identifier}",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            ProfileField("Propietario",
                if(residence.owner != null){
                    "${residence.owner.first_name} ${residence.owner.last_name}"
                    }else{
                        "Sin Propietario asignado"
                    }
                )
            ProfileField("Fecha creación", formatFecha(residence.created_at))
            ProfileField("Última modificación", formatFecha(residence.updated_at))
        }

        Spacer(modifier = Modifier.width(20.dp))

        // 📌 COLUMNA DERECHA (lista de residentes)
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                "Residentes actuales:",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (residence.residents.isEmpty()) {
                Text("No hay residentes asignados", color = Color.Gray)
            } else {
                residence.residents.forEach { user ->
                    Text(
                        "- ${user.first_name} ${user.last_name}",
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun UserSelectItem(
    user: AppUsers,
    isSelected: Boolean,
    onToggle: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(45.dp)
                .background(Color.DarkGray, RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text("${user.first_name} ${user.last_name}", color = Color.White)
            Text(user.email, color = Color.Gray, fontSize = 13.sp)
        }

        Checkbox(
            checked = isSelected,
            onCheckedChange = { onToggle(user.id) }
        )
    }
}

@Composable
fun ResidentEditableItem(
    resident: AppUsers,
    onRemove: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                "- ${resident.first_name} ${resident.last_name}",
                color = Color.White,
                fontSize = 16.sp
            )
        }

        Button(
            onClick = { onRemove(resident.id) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Icon(
                Icons.Default.Remove,
                "delete user icon",
                tint = Color.White
            )
        }
    }
}

@Composable
fun SaveChangesButton(
    enabled: Boolean,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E2AFF))
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Color.White)
        } else {
            Text(
                text = "Guardar cambios",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun AssignButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled:Boolean=false,
    isLoading:Boolean= false
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2E2AFF),
            contentColor = Color.White
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp),
        enabled = enabled
    ) {
        if(isLoading){
            CircularProgressIndicator(color = AppColors.Background)
        }else{
            Text(
                text = "Asignar usuarios",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
