package com.example.residente_app.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.residente_app.data.remote.DTO.AppUsers
import com.example.residente_app.viewmodel.GetUserDataState
import com.example.residente_app.viewmodel.UsersAppViewModel

@Composable
fun UserDetailScreen(
    userVm: UsersAppViewModel,
    userId:Int,
    onBack: () -> Unit
){
    LaunchedEffect(Unit) {
        userVm.getUser(userId)
    }

    val userState by userVm.getUserState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0F0F))
            .padding(20.dp)
    ) {

        Column {

            // ---------- HEADER ----------
            Row(verticalAlignment = Alignment.CenterVertically) {

                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }

                Text(
                    text = "Perfil del usuario",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            when (userState) {

                is GetUserDataState.Loading -> {
                    CircularProgressIndicator(color = Color.White)
                }

                is GetUserDataState.Error -> {
                    Text(
                        text = (userState as GetUserDataState.Error).message,
                        color = Color.Red
                    )
                }

                is GetUserDataState.Success -> {
                    val user = (userState as GetUserDataState.Success).user
                    ProfileCard(user)
                }

                else -> Unit
            }
        }
    }
}

@Composable
fun ProfileCard(user: AppUsers) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1C1C1C), RoundedCornerShape(20.dp))
            .padding(20.dp)
    ) {

        // Foto o icono
        Box(
            modifier = Modifier
                .size(90.dp)
                .background(Color(0xFF2E2AFF), RoundedCornerShape(100.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "${user.first_name} ${user.last_name}",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        ProfileField("Usuario", user.username)
        ProfileField("Correo", user.email)
        ProfileField("ID", user.id.toString())
    }
}

@Composable
fun ProfileField(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(label, color = Color(0xFF8E8E8E), fontSize = 14.sp)
        Text(value, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}