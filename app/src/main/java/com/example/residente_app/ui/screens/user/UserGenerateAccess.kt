package com.example.residente_app.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.residente_app.ui.screens.user.components.userInvites.InvitationsErrorState
import com.example.residente_app.ui.screens.user.components.userInvites.InvitationsLoadingState
import com.example.residente_app.ui.screens.user.components.userInvites.InviteGeneratedList
import com.example.residente_app.ui.screens.user.components.userInvites.InviteQrCard
import com.example.residente_app.ui.screens.user.components.userInvites.InviteTopHeader
import com.example.residente_app.viewmodel.GetUserInvitationsState
import com.example.residente_app.viewmodel.InvitationViewModel
import com.example.residente_app.viewmodel.UsersAppViewModel

@Composable
fun InvitePeopleScreen(
    onInviteRedirect: () -> Unit,
    inviteVM: InvitationViewModel,
    userVm: UsersAppViewModel
) {

    LaunchedEffect(Unit) {
        userVm.getUserResidenceInfo()
        inviteVM.getUserInvitations()
    }


    val userData by userVm.userResidenceData.collectAsState()
    val generatedInvitation by inviteVM.invitation.collectAsState()
    //USER INVITATIONA
    val userInvitations by inviteVM.userInvitationsList.collectAsState()
    val userInvitationState by inviteVM.getUserInvitationsState.collectAsState()

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
                    onInviteScreenRedirect = onInviteRedirect,
                    invitationContent = generatedInvitation,
                    userData = userData
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))

                when(userInvitationState){
                    is GetUserInvitationsState.Loading -> {
                        InvitationsLoadingState()
                    }

                    is GetUserInvitationsState.Error -> {
                        val message = (userInvitationState as GetUserInvitationsState.Error).message
                        InvitationsErrorState(
                            message=message
                        )
                    }
                    is GetUserInvitationsState.Success -> {
                        InviteGeneratedList(
                            userData=userData,
                            userInvitationsList = userInvitations
                        )
                    }

                    else -> Unit
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}