package com.example.residente_app.ui.screens.user.components.userInvites

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import com.example.residente_app.data.remote.DTO.InviteCreationResponse
import com.example.residente_app.data.remote.DTO.UserResidenceInfoResponse
import com.example.residente_app.ui.screens.user.components.ElegantDivider
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.CircularProgressIndicator


@Composable
fun InviteHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0E3D3B), RoundedCornerShape(24.dp))
            .padding(vertical = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Invitar personas",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
fun InviteSectionPlaceholder(title: String) {
    Column {
        Text(
            text = "$title:",
            color = Color.White,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .background(Color(0xFF1C1C1C), RoundedCornerShape(6.dp))
        )
    }
}


@Composable
fun InviteTopHeader(modifier: Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(
                Color(0xFF0F3D3A),
                RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Invitar personas",
            color = Color.White,
            fontSize = 18.sp
        )
    }
}

/* ------------------------------------
            QR SECTION
   ------------------------------------
 */

@Composable
fun InviteQrCard(
    onInviteScreenRedirect: () -> Unit,
    invitationContent: InviteCreationResponse?,
    userData: UserResidenceInfoResponse?
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .background(
                Color(0xFF1C1C1C),
                RoundedCornerShape(22.dp)
            )
            .padding(16.dp)
    ) {
        Column {

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                SmallActionChip(
                    "Invitar",
                    onClick = {onInviteScreenRedirect()}
                    )
            }

           Row(
               horizontalArrangement = Arrangement.Center,
               modifier = Modifier.fillMaxWidth()
                   .padding(bottom = 6.dp)
           ){
               Text(
                   "Invita a tus visitas",
                   color = Color.White,
                   fontSize = 22.sp,
                   fontWeight = FontWeight.Bold
                   )
           }
           Box(
               modifier = Modifier.fillMaxWidth(),
               contentAlignment = Alignment.Center
           ){
               QrPlaceholder()
           }

            Spacer(modifier = Modifier.height(14.dp))

            Spacer(modifier = Modifier.height(14.dp))

            ElegantDivider()

            Spacer(modifier = Modifier.height(14.dp))

            InviteInfoSection(
                inviteInformation = invitationContent,
                userData = userData
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                SmallActionChip("Compartir", onClick = {})
            }
        }
    }
}

@Composable
fun QrPlaceholder() {
    Box(
        modifier = Modifier
            .size(140.dp)
            .background(Color(0xFF6B5F5F), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text("qr", color = Color.White)
    }
}

@Composable
fun SmallActionChip(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = {onClick()},
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1C6C68), // 👈 tu color
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(
            horizontal = 14.dp,
            vertical = 6.dp
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp
        )
    ) {
        Text(text, color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun InviteInfoSection(
    inviteInformation: InviteCreationResponse?,
    userData: UserResidenceInfoResponse?
) {
    if (inviteInformation == null) return

    Column {
        Text(
            text = "Información",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        TwoColumnRow(
            left = {
                InfoField(
                    label = "Residencia",
                    value = inviteInformation.residence
                )
            },
            right = {
                InfoField(
                    label = "Anfitrión",
                    value = "${userData?.first_name.orEmpty()} ${userData?.last_name.orEmpty()}"
                )
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        TwoColumnRow(
            left = {
                InfoField(
                    label = "Motivo",
                    value = inviteInformation.reason
                )
            },
            right = {
                InfoField(
                    label = "Código",
                    value = inviteInformation.code.toString()
                )
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        TwoColumnRow(
            left = {
                InfoField(
                    label = "Válido hasta",
                    value = "15-12-2024 18:00" // luego formateas desde backend
                )
            },
            right = {
                InfoField(
                    label = "Nombre invitado",
                    value = inviteInformation.guest_name
                )
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo largo → ocupa 2 columnas
        InfoField(
            label = "Info referencia",
            value = inviteInformation.aditional_information
        )
    }
}


@Composable
private fun TwoColumnRow(
    left: @Composable () -> Unit,
    right: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(modifier = Modifier.weight(1f)) {
            left()
        }
        Box(modifier = Modifier.weight(1f)) {
            right()
        }
    }
}

@Composable
private fun InfoField(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 12.sp
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2
        )
    }
}

@Composable
fun InviteInfoRow(label: String, value: String) {
    Row {
        Text(label, color = Color.LightGray, fontSize = 13.sp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(value, color = Color.White, fontSize = 13.sp)
    }
}

/* -----------------------
        Invite section
 */

@Composable
fun InviteGeneratedList(
    userData: UserResidenceInfoResponse?,
    userInvitationsList: List<InviteCreationResponse>
) {
    Column(
        modifier = Modifier.padding(
            bottom = 84.dp,
            start = 20.dp,
            end = 20.dp
        )
    ) {

        Text(
            "Invitaciones generadas",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        if(userInvitationsList.isEmpty()){
            EmptyInvitationsState(

            )
        }else{
            userInvitationsList.map { invitation ->
                InviteGeneratedItem(
                    name = invitation.guest_name,
                    /*status = if (invitation.isExpired()) "Expirada" else "Válida",*/
                    status = "Expirada",
                    date = "15-03",
                    statusColor = Color.Green
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun EmptyInvitationsState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Outlined.MailOutline,
            contentDescription = "Sin invitaciones",
            tint = Color(0xFF8E8E93), // gris elegante
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Aún no has creado invitaciones",
            color = Color(0xFFB0B0B0),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Cuando generes una, aparecerá aquí",
            color = Color(0xFF7A7A7A),
            fontSize = 14.sp
        )
    }
}

@Composable
fun InvitationsLoadingState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = Color(0xFF1C6C68),
                strokeWidth = 3.dp
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = "Cargando invitaciones...",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun InvitationsErrorState(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFD32F2F),
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = Color(0xFF1C1C1C),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Error",
                tint = Color(0xFFD32F2F),
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = message,
                color = Color(0xFFD32F2F),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun InviteGeneratedItem(
    name: String,
    status: String,
    date: String,
    statusColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(name, color = Color.White)
            Text("Visita", color = Color.Gray, fontSize = 12.sp)
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(status, color = statusColor, fontSize = 12.sp)
            Text(date, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Composable
fun TopBackBar() {
    Icon(
        imageVector = Icons.Default.ArrowBackIosNew,
        contentDescription = "Back",
        tint = Color.White,
        modifier = Modifier.size(28.dp)
    )
}