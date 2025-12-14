package com.example.residente_app.ui.screens.user.components.userInvites

import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import com.example.residente_app.ui.screens.user.components.ElegantDivider


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
    onInviteScreenRedirect: () -> Unit
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

            Spacer(modifier = Modifier.height(14.dp))


           Box(
               modifier = Modifier.fillMaxWidth(),
               contentAlignment = Alignment.Center
           ){
                Text("Invita a tus visitas", color = Color.White)
               QrPlaceholder()
           }

            Spacer(modifier = Modifier.height(14.dp))

            Spacer(modifier = Modifier.height(14.dp))

            ElegantDivider()

            Spacer(modifier = Modifier.height(14.dp))

            InviteInfoSection()
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
fun InviteInfoSection() {
    Column {
        Text(
            text = "Información",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Fila 1
        Row(modifier = Modifier.fillMaxWidth()) {
            InfoField(
                label = "Residencia",
                value = "H2",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            InfoField(
                label = "Anfitrión",
                value = "Username",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🔹 Fila 2
        Row(modifier = Modifier.fillMaxWidth()) {
            InfoField(
                label = "Código",
                value = "53214",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            InfoField(
                label = "Válido hasta",
                value = "15-12-2024 18:00",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Campos largos (columna)
        InfoField(
            label = "Nombre invitado",
            value = "Ivan Cabrera"
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoField(
            label = "Info referencia",
            value = "-"
        )
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
fun InviteGeneratedList() {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Text(
            "Invitaciones generadas",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        InviteGeneratedItem(
            name = "Lucas Hernandez",
            status = "Valida",
            date = "vie-12",
            statusColor = Color(0xFF6BD3B0)
        )

        Spacer(modifier = Modifier.height(12.dp))

        InviteGeneratedItem(
            name = "Luis Sandoval",
            status = "Expirada",
            date = "sáb-12",
            statusColor = Color.Red
        )

        InviteGeneratedItem(
            name = "Luis Sandoval",
            status = "Expirada",
            date = "sáb-12",
            statusColor = Color.Red
        )
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