package com.example.residente_app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.residente_app.ui.screens.HomeScreen
import com.example.residente_app.ui.screens.LoginScreen
import com.example.residente_app.ui.screens.ProfileScreen
import com.example.residente_app.viewmodel.LoginViewModel

@Composable
fun AppNavigation(vm: LoginViewModel){
    val nav = rememberNavController()

    NavHost(nav, startDestination = "home"){
        composable("home"){
            HomeScreen(
                onLogin = { nav.navigate("Login")}
            )
        }
        composable("profile"){
            ProfileScreen()
        }
        composable("login"){
            LoginScreen(vm, onSuccess = {nav.navigate("profile")})
        }
    }
}