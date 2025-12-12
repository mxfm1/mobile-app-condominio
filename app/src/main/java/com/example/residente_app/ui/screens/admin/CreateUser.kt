package com.example.residente_app.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.residente_app.viewmodel.CreateUserForm
import com.example.residente_app.viewmodel.CreateUserState
import com.example.residente_app.viewmodel.UsersAppViewModel
import com.example.residente_app.ui.theme.AppColors
import androidx.compose.material3.TextFieldDefaults
import com.example.residente_app.ui.components.CustomInputWithError


@Composable
fun CreateUserScreen(
    vm: UsersAppViewModel,
    onBack: () -> Unit,
    onSuccess:() -> Unit
) {
    val formState by vm.form.collectAsState()
    val uiState by vm.state.collectAsState()
    val fieldErrors = (uiState as? CreateUserState.FieldErrors)?.errors

    LaunchedEffect(uiState) {
        if(uiState is CreateUserState.Success){
            vm.clearFields()

            kotlinx.coroutines.delay(3000)
            vm.clearState()
            onSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.TextPrimary)

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            // ---------------- HEADER ----------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Registra un usuario",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // ---------------- FORM ----------------
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A1A1A), RoundedCornerShape(26.dp))
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // ------------ USERNAME ------------
                CustomInputWithError(
                    value = formState.username,
                    onValueChange = { vm.updateField("username", it) },
                    label = "Nombre de usuario",
                    error = fieldErrors?.username?.firstOrNull()
                )

                // ------------ EMAIL ------------
                CustomInputWithError(
                    value = formState.email,
                    onValueChange = { vm.updateField("email", it) },
                    label = "Correo electronico",
                    error = fieldErrors?.email?.firstOrNull()
                )

                // ------------ FIRST NAME ------------
                CustomInputWithError(
                    value = formState.first_name,
                    onValueChange = { vm.updateField("first_name", it) },
                    label = "Primer Nombre",
                    error = fieldErrors?.first_name?.firstOrNull()
                )

                // ------------ LAST NAME ------------
                CustomInputWithError(
                    value = formState.last_name,
                    onValueChange = { vm.updateField("last_name", it) },
                    label = "Segundo Nombre",
                    error = fieldErrors?.last_name?.firstOrNull()
                )

                // ------------ PASSWORD ------------
                CustomInputWithError(
                    value = formState.password,
                    onValueChange = { vm.updateField("password", it) },
                    label = "Contraseña",
                    error = fieldErrors?.password?.firstOrNull(),
                    isPassword = true
                )

                // ------------ PASSWORD 2 ------------
                CustomInputWithError(
                    value = formState.password2,
                    onValueChange = { vm.updateField("password2", it) },
                    label = "Confirma la contraseña",
                    error = fieldErrors?.password2?.firstOrNull(),
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(4.dp)) // ------------ SUBMIT BUTTON ------------
                Button(
                    onClick = { vm.createUser(formState) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Primary
                    )
                ) {
                    if(uiState is CreateUserState.Loading){
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = Color.Black,
                            strokeWidth = 3.dp
                        )
                    }else{
                        Text(
                            "Registrar usuario",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }

                // ------------ UI STATE FEEDBACK ------------
                when (uiState) {
                    is CreateUserState.Success -> {
                        val msg = uiState.message ?: "User creado"
                        Text(msg, color = Color.Green)
                    }

                    is CreateUserState.Error -> {
                        val error = uiState as CreateUserState.Error
                        val msg = error.message
                        Text("Error al crear usuario ❌: ${msg}}", color = Color.Red)
                    }
                    else -> {}
                }
            }
        }
    }
}

/* ------------------------------
   TEXTFIELD DARK STYLE
--------------------------------*/
@Composable
fun textFieldDark() = TextFieldDefaults.colors(
    focusedIndicatorColor = AppColors.Primary,
    unfocusedIndicatorColor = Color.DarkGray,
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedLabelColor = AppColors.Primary,
    unfocusedLabelColor = Color.Gray,
    cursorColor = AppColors.Primary,
    focusedContainerColor = Color(0xFF1A1A1A),
    unfocusedContainerColor = Color(0xFF1A1A1A)
)
