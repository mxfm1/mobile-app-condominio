package com.example.residente_app.ui.layout

import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import com.example.residente_app.ui.components.AppBar
import DrawerBody
import DrawerHeader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.residente_app.ui.components.Menuitem
import com.example.residente_app.ui.theme.AppColors

@Composable
fun AuthenticatedLayout(
    items: List<Menuitem>,
    onItemClick: (Menuitem) -> Unit,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet (
                modifier = Modifier.padding(),
                drawerContainerColor = AppColors.TextPrimary,
            ){
                CustomDrawerHeader("Hola Nuevamente")
                CustomDrawerBody(
                    items = items,
                    onItemClick = {
                        onItemClick(it)
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                AppBar(
                    onNavigationIconClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)){
                content()
            }
        }
    }
}

@Composable
fun CustomDrawerHeader(header:String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2E2AFF))
            .padding(24.dp)
    ) {
        Text(
            header,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}
@Composable
fun CustomDrawerBody(
    items: List<Menuitem>,
    onItemClick: (Menuitem) -> Unit
) {
    items.forEach { item ->
        NavigationDrawerItem(
            label = {
                Text(
                    text = item.title,
                    color = Color.White      // color del texto
                )
            },
            selected = false,
            onClick = { onItemClick(item) },
            icon = {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = Color.White      // color del icono
                )
            },
            colors = NavigationDrawerItemDefaults.colors(
                unselectedTextColor = Color.White,
                selectedTextColor = Color.Cyan,
                unselectedIconColor = Color.White,
                selectedIconColor = Color.Cyan,
                selectedContainerColor = Color(0x332E2AFF),
                unselectedContainerColor = Color.Transparent
            )
        )
    }
}
