package com.example.residente_app

import DrawerHeader
import DrawerBody
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.residente_app.ui.components.AppBar
import com.example.residente_app.ui.theme.ResidenteAppTheme
import com.example.residente_app.ui.AppNavigation
import com.example.residente_app.ui.components.Menuitem
import com.example.residente_app.viewmodel.AuthViewModelFactory
import com.example.residente_app.viewmodel.LoginViewModel
import com.example.residente_app.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val vm: LoginViewModel by viewModels {
        AuthViewModelFactory(application)
    }

    private val userVm: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ResidenteAppTheme {
                AppNavigation(vm, userVm)
            }
        }
    }
}
