package com.example.residente_app.ui.components.layouts.User

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val userNavItems = listOf(
    UserNavItem("Inicio", Icons.Default.Home, "user/home"),
    UserNavItem("Accesos", Icons.Default.AddHome, "user/access"),
    UserNavItem("Pedidos", Icons.Default.Inbox, "user/packages"),
    UserNavItem("Perfil", Icons.Default.Person, "user/profile")
)

@Composable
fun UserLayout(
    onItemClick: (route:String) -> Unit,
    currentRoute:String?,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0F0F))
    ) {

        // Contenido principal de cada pantalla
        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }

        // Navbar flotante
        FloatingBottomNavBar(
            items = userNavItems,
            currentRoute = currentRoute,
            onNavigate = { route -> onItemClick(route)},
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp, vertical = 20.dp)
        )
    }
}