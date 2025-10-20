package com.example.residente_app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.residente_app.ui.components.HeaderTextComponent

@Composable
fun ProfileScreen(){
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        HeaderTextComponent("Profile Section")
    }
}