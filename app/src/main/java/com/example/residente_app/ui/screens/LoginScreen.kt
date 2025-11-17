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
import com.example.residente_app.viewmodel.UserViewModel
import com.example.residente_app.viewmodel.ApiLoginState


@Composable
fun LoginScreen(
    vm: LoginViewModel,
    onSuccess: () -> Unit,
    userVm: UserViewModel,
    redirectTo: () -> Unit
){
    //val form = vm.form.collectAsState()
    val formState by vm.form.collectAsState()
    val state by vm.state.collectAsState()

    val apiState by userVm.loginResult.collectAsState()

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
                    redirectTo()
                }
            ) {
                Text("Ir al inicio")
            }

            Button(
                onClick = { userVm.loginUserFromRESTAPI(formState.username,formState.password)},
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Iniciar sesión con API Rest")
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier

                    .padding(16.dp)
            )

            when (apiState) {
                is ApiLoginState.Loading -> {
                    CircularProgressIndicator()
                }

                is ApiLoginState.Error -> {
                    val msg = (apiState as ApiLoginState.Error).error
                    Text(
                        text = "ERROR: $msg",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                is ApiLoginState.Success -> {
                    Text(
                        text = "Login exitoso",
                        color = Color.Green,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    onSuccess()
                }

                else -> Unit
            }
        }
    }
}
