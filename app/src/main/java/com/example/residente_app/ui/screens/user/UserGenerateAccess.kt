package com.example.residente_app.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.residente_app.ui.screens.user.components.userInvites.InviteGeneratedList
import com.example.residente_app.ui.screens.user.components.userInvites.InviteQrCard
import com.example.residente_app.ui.screens.user.components.userInvites.InviteTopHeader

@Composable
fun InvitePeopleScreen(
    onInviteRedirect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        // 🔹 HEADER FIJO
        InviteTopHeader(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .zIndex(1f)
        )

        // 🔹 CONTENIDO SCROLLEABLE
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 72.dp) // ⬅️ espacio para el header
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))

                InviteQrCard(
                    onInviteScreenRedirect = onInviteRedirect
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                InviteGeneratedList()
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}