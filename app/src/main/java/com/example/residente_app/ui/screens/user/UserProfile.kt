package com.example.residente_app.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.residente_app.ui.components.buttons.LogoutButton
import com.example.residente_app.viewmodel.UserViewModel
import com.example.residente_app.viewmodel.UsersAppViewModel

@Composable
fun UserProfileScreen(
    userVm: UserViewModel,
    userAppVm: UsersAppViewModel
) {
    LaunchedEffect(Unit) {
        userAppVm.getUserResidenceInfo()
    }

    val userInfo by userAppVm.userResidenceData.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            // 👋 Saludo
            Text(
                text = "Hola ${userInfo?.username ?: ""}",
                fontSize = 28.sp,
                color = Color.White
            )

            // 🖼 Placeholder imagen
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }

            Divider(color = Color.Gray)

            // 👤 Datos del usuario
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "${userInfo?.first_name} ${userInfo?.last_name}",
                    color = Color.White,
                    fontSize = 20.sp
                )
                Text(
                    text = userInfo?.email ?: "",
                    color = Color.LightGray
                )
            }

            // 🏠 Residencia
            userInfo?.residence?.let { residence ->
                Column {
                    Divider(color = Color.Gray)
                    Text(
                        text = "Residencia",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                    Text(
                        text = residence.identifier,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }

            Divider(color = Color.Gray)

            // ⚙️ Opciones
            ProfileOption("Perfil", Icons.Default.Person)
            ProfileOption("Configuración", Icons.Default.Settings)
            ProfileOption("Mi residencia", Icons.Default.Home)
            ProfileOption("Notificaciones", Icons.Default.Notifications)
            ProfileOption("Ayuda", Icons.Default.Help)

            // 🚪 Logout
            LogoutButton(
                label = "Cerrar sesión",
                onLogout = { userVm.logoutUser() },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun ProfileOption(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.White)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            color = Color.White,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}