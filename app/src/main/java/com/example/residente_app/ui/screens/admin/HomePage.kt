package com.example.residente_app.ui.screens.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.residente_app.ui.components.buttons.LogoutButton
import com.example.residente_app.viewmodel.UserViewModel

// ----------------------------
// DATA MODELS (EJEMPLO)
// ----------------------------

data class Casa(
    val nombre: String,
    val dueno: String,
    val habitantes: Int,
    val fechaRegistro: String
)

data class UsuarioRegistrado(
    val nombre: String,
    val casa: String,
    val fecha: String
)

val ultimasCasas = listOf<Casa>(
    Casa(
        "H2",
        dueno = "Luis Hernandez",
        habitantes = 3,
        fechaRegistro = "12-12-2025"
    ),
    Casa(
        "H2",
        dueno = "Luis Hernandez",
        habitantes = 3,
        fechaRegistro = "12-12-2025"
    ),
    Casa(
        "H2",
        dueno = "Luis Hernandez",
        habitantes = 3,
        fechaRegistro = "12-12-2025"
    ),
)

val ultimosUsuarios = listOf<UsuarioRegistrado>(
    UsuarioRegistrado(
        nombre = "Felipe Machuca",
        "H2",
        fecha = "12-12-2025"
    )
)
// ----------------------------
// ADMIN HOME PAGE
// ----------------------------

@Composable
fun AdminHomePage(
    userVm: UserViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(
                text = "Bienvenido Administrador 👋",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Text(
                text = "Últimos domicilios registrados",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
        }

        items(ultimasCasas.take(5)) { casa ->
            CasaCard(casa)
        }

        // ----------------------------
        // SECCIÓN: ÚLTIMOS USUARIOS
        // ----------------------------
        item {
            Text(
                text = "Últimos usuarios añadidos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        items(ultimosUsuarios.take(5)) { usuario ->
            UsuarioItem(usuario)
        }

        item {
            LogoutButton(
                "Cerrar sesion",
                onLogout = { userVm.logoutUser()},

                )
        }
    }
}

// ----------------------------
// CARD DE DOMICILIO
// ----------------------------
@Composable
fun CasaCard(casa: Casa) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ÍCONO DE CASA
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = casa.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Dueño: ${casa.dueno}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // COLUMNA DERECHA
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Habitantes: ${casa.habitantes}",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = casa.fechaRegistro,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

// ----------------------------
// ITEM DE USUARIO REGISTRADO
// ----------------------------
@Composable
fun UsuarioItem(usuario: UsuarioRegistrado) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // AVATAR
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = usuario.nombre,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Casa: ${usuario.casa}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Text(
            text = usuario.fecha,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}
