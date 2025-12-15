package com.example.residente_app.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.residente_app.data.remote.DTO.InviteCreationRequest
import com.example.residente_app.viewmodel.CreateInvitationState
import com.example.residente_app.viewmodel.InvitationViewModel
import com.example.residente_app.viewmodel.UserViewModel
import com.example.residente_app.viewmodel.UsersAppViewModel

data class InviteFormState(
    val guest_name:String = "",
    val reason:String = "Visita",
    val minutesAdded:Int = 30,
    val additionalInfo:String = ""
)

@Composable
fun CreateInviteScreen(
    onScreenBack: () -> Unit,
    inviteVM: InvitationViewModel,
    userVm: UsersAppViewModel
) {
    val userData by userVm.userResidenceData.collectAsState()

    val requestState by inviteVM.createInviteState.collectAsState()
    val formState = rememberInviteFormState()
    val form = formState.value

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(requestState) {
        when (requestState) {
            is CreateInvitationState.Error -> {
                val message = (requestState as CreateInvitationState.Error).message
                if (!message.isNullOrEmpty()) {
                    snackbarHostState.showSnackbar(message)
                }
                inviteVM.resetInvitationCreationState()
            }

            is CreateInvitationState.Success -> {
                val message = (requestState as CreateInvitationState.Success).message
                if (!message.isNullOrEmpty()) {
                    snackbarHostState.showSnackbar(message)
                }
                inviteVM.resetInvitationCreationState()
                onScreenBack()
            }

            else -> Unit
        }
    }

    //Averiguar info del usuario
    LaunchedEffect(Unit) {
        userVm.getUserResidenceInfo()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            HeaderSection(
                onFormBack = {onScreenBack()}
            )

            Spacer(modifier = Modifier.height(32.dp))

            InviteFormSection(
                form=form,
                onFormChange={formState.value = it}
            )
        }
        val residenceId = userData?.residence?.identifier

        CreateInviteButton(
            isLoading = requestState is CreateInvitationState.Loading,
            onClick = {
                if(residenceId == null) return@CreateInviteButton
               val request = InviteCreationRequest(
                   guest_name = form.guest_name,
                   reason = form.reason,
                   valid_until = "2025-12-15T18:30:00Z",
                   aditional_information = form.additionalInfo,
                   residence = residenceId
               )
                inviteVM.createInvitation(request)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
        )
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
@Composable
fun rememberInviteFormState(): MutableState<InviteFormState> {
    return remember {
        mutableStateOf(InviteFormState())
    }
}

@Composable
private fun HeaderSection(
    onFormBack: ()-> Unit
) {
    Button(
        onClick = {onFormBack()},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1C6C68),
            contentColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Volver",
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
private fun InviteFormSection(
    form: InviteFormState,
    onFormChange: (InviteFormState) -> Unit
) {
    Column {

        FormLabel("Nombre invitado")
        TextInput(
            value = form.guest_name,
            onChange = { onFormChange(form.copy(guest_name = it)) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        FormLabel("Motivo")
        ReasonDropdown(
            selected = form.reason,
            onSelect = { onFormChange(form.copy(reason = it)) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        FormLabel("Válido hasta")
        DurationSelector(
            selectedMinutes = form.minutesAdded,
            onSelect = { onFormChange(form.copy(minutesAdded = it)) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        FormLabel("Información adicional")
        TextAreaInput(
            value = form.additionalInfo,
            onChange = { onFormChange(form.copy(additionalInfo = it)) }
        )
    }
}


/* --------------------------
            FORM COMPONENTS
   --------------------------
 */
@Composable
private fun FormLabel(text: String) {
    Text(text, color = Color.White, fontSize = 14.sp)
}

@Composable
private fun TextInput(
    value: String,
    onChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
private fun ReasonDropdown(
    selected: String,
    onSelect: (String) -> Unit
) {
    TextField(
        value = selected,
        onValueChange = onSelect,
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
private fun MockInput(value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(
                width = 1.dp,
                color = Color(0xFF2EA8FF),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(value, color = Color.Gray)
    }
}

@Composable
private fun TextAreaInput(
    value: String,
    onChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    )
}

@Composable
private fun DurationSelector(
    selectedMinutes: Int,
    onSelect: (Int) -> Unit
) {
    Row {
        listOf(30, 60, 120, 240).forEach { minutes ->
            DurationChip(
                text = "${minutes / 60} hr",
                selected = minutes == selectedMinutes,
                onClick = { onSelect(minutes) }
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
private fun DurationChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .background(
                if (selected) Color(0xFF1C6C68) else Color(0xFF1A1A1A),
                RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(text, color = Color.White)
    }
}

@Composable
private fun CreateInviteButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onClick:() -> Unit
) {
    Button(
        onClick = onClick,
        enabled = !isLoading,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1C6C68)
        )
    ) {
       if(isLoading){
           CircularProgressIndicator(
               modifier = Modifier.size(22.dp),
               color = Color.White,
               strokeWidth = 2.dp
           )
       }else{
           Text(
               text = "Crear invitación",
               color = Color.White,
               fontSize = 16.sp
           )
       }
    }
}

