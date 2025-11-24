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


@Composable
fun CreateUserScreen(
    vm: UsersAppViewModel,
    onBack: () -> Unit
) {
    val formState by vm.form.collectAsState()
    val uiState by vm.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.TextPrimary)
            .padding(16.dp)
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
                OutlinedTextField(
                    value = formState.username,
                    onValueChange = { vm.updateField("username", it) },
                    label = { Text("Nombre de usuario") },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    colors = textFieldDark()
                )

                // ------------ EMAIL ------------
                OutlinedTextField(
                    value = formState.email,
                    onValueChange = { vm.updateField("email", it) },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    colors = textFieldDark()
                )

                // ------------ FIRST NAME ------------
                OutlinedTextField(
                    value = formState.first_name,
                    onValueChange = { vm.updateField("first_name", it) },
                    label = { Text("Nombre") },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    colors = textFieldDark()
                )

                // ------------ LAST NAME ------------
                OutlinedTextField(
                    value = formState.last_name,
                    onValueChange = { vm.updateField("last_name", it) },
                    label = { Text("Apellido") },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    colors = textFieldDark()
                )

                // ------------ PASSWORD ------------
                OutlinedTextField(
                    value = formState.password,
                    onValueChange = { vm.updateField("password", it) },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = textFieldDark()
                )

                // ------------ PASSWORD 2 ------------
                OutlinedTextField(
                    value = formState.password2,
                    onValueChange = { vm.updateField("password2", it) },
                    label = { Text("Repite la contraseña") },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = textFieldDark()
                )

                Spacer(modifier = Modifier.height(4.dp))

                // ------------ SUBMIT BUTTON ------------
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
                    Text(
                        "Registrar usuario",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                // ------------ UI STATE FEEDBACK ------------
                when (uiState) {
                    is CreateUserState.Loading ->
                        CircularProgressIndicator(color = AppColors.Primary)

                    is CreateUserState.Success ->
                        Text("Usuario creado correctamente 🎉", color = Color.Green)

                    is CreateUserState.Error ->
                        Text("Error al crear usuario ❌", color = Color.Red)

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
