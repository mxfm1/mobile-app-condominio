package com.example.residente_app.ui.screens.testScreens
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun PrivacyPolicyScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Text("Policy Screen")
    }
}

@Composable
fun TermsAndConditionsScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Text("Terms Screen")
    }
}