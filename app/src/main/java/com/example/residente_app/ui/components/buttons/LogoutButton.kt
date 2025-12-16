package com.example.residente_app.ui.components.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.residente_app.ui.theme.AppColors

@Composable
fun LogoutButton(
    label:String,
    modifier: Modifier = Modifier,
    onLogout:() -> Unit
){
    Button(
        onClick = {onLogout()},
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            contentColor = AppColors.Angry
        )
    ){
        Icon(
            imageVector = Icons.Default.Logout,
            contentDescription = "LogoutButton"
        )
        Text(label, modifier = Modifier.padding(horizontal = 8.dp))
    }
}