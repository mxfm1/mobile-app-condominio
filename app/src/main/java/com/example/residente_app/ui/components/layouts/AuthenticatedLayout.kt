package com.example.residente_app.ui.layout

import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import com.example.residente_app.ui.components.AppBar
import DrawerBody
import DrawerHeader
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.example.residente_app.ui.components.Menuitem

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
                //modifier = Modifier.padding()
            ){
                DrawerHeader()
                DrawerBody(
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
