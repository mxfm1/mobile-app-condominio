package com.example.residente_app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.residente_app.ui.components.HeaderTextComponent
import com.example.residente_app.ui.components.buttons.LogoutButton
import com.example.residente_app.viewmodel.LoginState
import kotlinx.coroutines.launch
import com.example.residente_app.viewmodel.UserViewModel

@Composable
fun ProfileScreen(userVm: UserViewModel,onLogoutSuccess:() -> Unit){

    val apiState by userVm.loginState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize().
        padding(16.dp)
    ){
        HeaderTextComponent("Profile Section")
        LogoutButton("Cerrar ses", onLogout = {userVm.logoutUser()})
    }

}