package com.example.residente_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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

@Composable
fun LoginScreen(
    vm: LoginViewModel,
    onSuccess: () -> Unit
){
    //val form = vm.form.collectAsState()
    val formState by vm.form.collectAsState()
    val state by vm.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            NormalTextComponent("Hola denuevo")
            HeaderTextComponent(value = "Inicia sesion")

            Spacer(modifier = Modifier.height(20.dp))


            OutlinedTextField(
                value = formState.username,
                onValueChange = vm::onUsernameChange,
                label = { Text("Usuario") },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null)},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = formState.password,
                onValueChange = vm::onPasswordChange,
                label = {Text("Contraseña")},
                leadingIcon = { Icon(Icons.Filled.Lock,contentDescription = null)},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    scope.launch { vm.login() }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = state !is LoginState.Loading
            ) {
                if (state is LoginState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 8.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }
                Text("Iniciar sesión")
            }

            Button(
                onClick = {
                    vm.seedAdminUser()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Crear Admin User")
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier

                    .padding(16.dp)
            )

            LaunchedEffect(state) {
                when (state) {
                    is LoginState.Success -> {
                        onSuccess()
                        snackbarHostState.showSnackbar((state as LoginState.Success).message)
                        // Aquí podrías navegar, por ejemplo:
                        // navController.navigate("home")
                    }
                    is LoginState.Error -> {
                        snackbarHostState.showSnackbar((state as LoginState.Error).message)
                    }
                    else -> Unit
                }
            }

            //TextField("Usuario", icon = Icons.Filled.Person)
            //TextField("Contraseña", icon = Icons.Filled.Lock)
            //TextField("Correo", icon = Icons.Filled.Email)

            //PasswordField(label = "Password", icon = Icons.Filled.Lock)
//            CheckboxField(label = "Al aceptar nuestras Políticas de Servicio y nuestros Términos y condiciones",
//                onTextSelected = { clickedText ->
//                    when(clickedText){
//                        "Políticas de Servicio" -> {
//                        }
//                        "Términos y condiciones" -> {
//                            // Aquí navegas a la pantalla de términos
//
//                        }
//                    }
//                })

        }
    }
}
