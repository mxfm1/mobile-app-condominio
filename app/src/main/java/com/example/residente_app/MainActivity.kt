    package com.example.residente_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.residente_app.ui.screens.HomeScreen
import com.example.residente_app.ui.theme.ResidenteAppTheme

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.residente_app.ui.AppNavigation
import com.example.residente_app.ui.screens.testScreens.PrivacyPolicyScreen
import com.example.residente_app.ui.screens.testScreens.TermsAndConditionsScreen
import com.example.residente_app.viewmodel.AuthViewModelFactory
import com.example.residente_app.viewmodel.LoginViewModel

    class MainActivity : ComponentActivity() {

        private val vm: LoginViewModel by viewModels {
            AuthViewModelFactory(application)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResidenteAppTheme {
                AppNavigation(vm)
            }
        }
    }
}