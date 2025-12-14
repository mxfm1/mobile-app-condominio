package com.example.residente_app.ui.screens.user.components.userHomePage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.sp
import com.example.residente_app.ui.screens.user.components.ElegantDivider
import com.example.residente_app.ui.theme.AppColors

data class UserInfoRow(
    val label:String,
    val icon: ImageVector
)

val userInfoList:List<UserInfoRow> = listOf(
    UserInfoRow(
        "No hay pedidos por recibir",
        icon = Icons.Default.Inventory2
    ),
    UserInfoRow(
        "Gastos comunes al dia",
        icon = Icons.Default.AccountBalanceWallet
    ),
    UserInfoRow(
        "No hay mensajes nuevos de administración",
        icon = Icons.Default.Inbox
    ),
)

@Composable
fun WelcomeHeader(userName: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Bienvenido $userName",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        NotificationBell {}
    }
}

@Composable
fun NotificationBell(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(Color(0xFF1A1A1A), CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Default.Notifications,
            contentDescription = "Notifications",
            tint = Color.White
        )
    }
}

@Composable
fun UserInfoCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1A1A1A), RoundedCornerShape(20.dp))
            .padding(20.dp)
    ) {
        userInfoList.map { infoRow ->
            InfoRow(infoRow.label,infoRow.icon)
        }
    }
}

@Composable
fun InfoRow(
    text: String,
    icon: ImageVector,
    iconTint: Color = Color.White
) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            "null",
            tint = iconTint,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text, color = Color.White)
    }
}

// ---------------------------------------------------------
// Informacion de la residencia
// --------
// -------------------------------------------------
@Composable
fun MyHouseSection(
    ownerName: String,
    houseId: String,
    residentCount: Int,
    onGetHouseInfo: () -> Unit
) {
    Column {
        Text(
            text = "Mi Casa",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1C1C1C), RoundedCornerShape(20.dp))

        ) {

            Column {
                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                        .background(Color(0xFF1C6C68),
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 12.dp,        // ✔ superior derecha
                                bottomEnd = 0.dp,
                                bottomStart = 12.dp    // ✔ inferior izquierda
                            ))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(houseId, color = Color.White)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // 🔹 Info propietario
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Propietario", color = Color.White)
                        Text(
                            ownerName,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // 🔹 Cantidad de residentes
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = residentCount.toString(),
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Residentes",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = onGetHouseInfo,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1C6C68)
                    ),
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                ) {
                    Text("Ver Información", color = Color.White)
                }
            }
        }
    }
}
// ---------------------------------------------------------
// RECENT ACCESS LIST
// ---------------------------------------------------------
data class RecentAccess(
    val name: String,
    val tag: String,
    val time: String,
    val day: String
)

val mockList = listOf<RecentAccess>(
    RecentAccess(
        "Luis Hernandez",
        tag="visita",
        time="15:03",
        day="sáb-12"
    ),
    RecentAccess(
        "Luis Hernandez",
        tag="visita",
        time="15:03",
        day="sáb-12"
    ),
    RecentAccess(
        "Luis Hernandez",
        tag="visita",
        time="15:03",
        day="sáb-12"
    ),
    RecentAccess(
        "Luis Hernandez",
        tag="visita",
        time="15:03",
        day="sáb-12"
    ),
    RecentAccess(
        "Luis Hernandez",
        tag="visita",
        time="15:03",
        day="sáb-12"
    ),
)

@Composable
fun RecentAccessList() {
    Column {
        mockList.forEachIndexed { index, access ->
            RecentAccessItem(access)
            if(index > mockList.lastIndex){
                ElegantDivider()
            }
        }

    }
}


@Composable
fun RecentAccessItem(access: RecentAccess) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(access.name, color = Color.White, fontWeight = FontWeight.Bold)
            Text(
                access.tag,
                modifier = Modifier.padding(
                    start = 4.dp,
                ),
                color = Color.Gray)
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(access.time, color = Color.White)
            Text(access.day, color = Color.Gray)
        }
    }
}