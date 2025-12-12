package com.example.residente_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.residente_app.ui.components.CheckboxField
import com.example.residente_app.ui.components.HeaderTextComponent
import com.example.residente_app.ui.components.NormalTextComponent
import com.example.residente_app.ui.components.PasswordField
import com.example.residente_app.ui.components.TextField
import com.example.residente_app.viewmodel.LoginViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import com.example.residente_app.viewmodel.LoginState
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.residente_app.ui.theme.AppColors
import com.example.residente_app.viewmodel.UserViewModel
import com.example.residente_app.viewmodel.ApiLoginState


@Composable
fun LoginScreen(
    vm: LoginViewModel,
    onSuccess: () -> Unit,
    userVm: UserViewModel,
    redirectTo: () -> Unit
) {
    //val form = vm.form.collectAsState()
    val formState by vm.form.collectAsState()
    val apiState by userVm.loginState.collectAsState()

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0A0A0A),
            Color(0xFF11122A),
            Color(0xFF1E1E60),
            Color(0xFF2E2AFF)
        )
    )

    LaunchedEffect(apiState) {
        when(apiState){
            is ApiLoginState.Success -> {
                onSuccess()
                userVm.resetState()
            }
            else -> Unit
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(28.dp)
    ){
        Column(
            modifier = Modifier.fillMaxSize().background(gradient),

        ){
            Column {
                NormalTextComponent("Hola denuevo")
                HeaderTextComponent(value = "Inicia sesion")
            }

            Spacer(modifier = Modifier.height(20.dp))


            Column(
                modifier = Modifier.padding(top = 200.dp)
            ){
                OutlinedTextField(
                    value = formState.username,
                    onValueChange = vm::onUsernameChange,
                    label = { Text("Usuario") },
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null)},
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                        cursorColor = Color.White,
                        focusedLeadingIconColor = Color.White,
                        unfocusedLeadingIconColor = Color.White.copy(alpha = 0.7f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = formState.password,
                    onValueChange = vm::onPasswordChange,
                    label = {Text("Contraseña")},
                    leadingIcon = { Icon(Icons.Filled.Lock,contentDescription = null)},
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                        cursorColor = Color.White,
                        focusedLeadingIconColor = Color.White,
                        unfocusedLeadingIconColor = Color.White.copy(alpha = 0.7f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { userVm.loginUser(formState.username,formState.password)},
                    modifier = Modifier.fillMaxWidth()
                        .shadow(
                            elevation = 12.dp,          // ⭐ AQUI LE DAS LA SOMBRA
                            shape = RoundedCornerShape(20.dp),
                            clip = false
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.TextPrimary,
                        contentColor = AppColors.Background
                    ),
                    shape = RoundedCornerShape(20.dp),
                    enabled = apiState !is ApiLoginState.Loading
                ) {
                    if(apiState is ApiLoginState.Loading){
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(20.dp)
                        )
                        } else {
                            Text(
                                "Iniciar sesión",
                                color = Color.White
                            )
                        }
                    }
                }
            }
            when (apiState) {
                is ApiLoginState.Success -> {
                    vm.onSuccessForm()
                }
                is ApiLoginState.Error -> {
                    Text(
                        text = (apiState as ApiLoginState.Error).error,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> Unit
            }
        }
}
