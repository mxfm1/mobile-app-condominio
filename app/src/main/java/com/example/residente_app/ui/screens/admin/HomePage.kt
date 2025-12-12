    package com.example.residente_app.ui.screens.admin

    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.items
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Home
    import androidx.compose.material.icons.filled.Person
    import androidx.compose.material3.*
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Brush
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import com.example.residente_app.data.remote.DTO.AppUsers
    import com.example.residente_app.data.remote.DTO.ResidenceResponse
    import com.example.residente_app.ui.components.buttons.AgregarUsuariosButton
    import com.example.residente_app.ui.components.buttons.CustomAddIconButton
    import com.example.residente_app.ui.components.buttons.LogoutButton
    import com.example.residente_app.ui.components.buttons.PrimaryButton
    import com.example.residente_app.ui.theme.AppColors
    import com.example.residente_app.viewmodel.UserViewModel
    import com.example.residente_app.ui.components.sections.SectionWithBackground
    import com.example.residente_app.util.formatFecha
    import com.example.residente_app.viewmodel.GetUsersState
    import com.example.residente_app.viewmodel.ResidenceGetState
    import com.example.residente_app.viewmodel.ResidenceViewModel
    import com.example.residente_app.viewmodel.UsersAppViewModel

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
        Casa(
            "F5",
            dueno = "Lucas Castro",
            habitantes = 2,
            fechaRegistro = "12-12-2025"
        ),
    )

    val ultimosUsuarios = listOf<UsuarioRegistrado>(
        UsuarioRegistrado(
            nombre = "Felipe Machuca",
            "H2",
            fecha = "12-12-2025"
        ),
        UsuarioRegistrado(
            nombre = "Lucas Hernandez",
            "F7",
            fecha = "12-12-2025"
        ),
        UsuarioRegistrado(
            nombre = "Luis Tapia",
            "F1",
            fecha = "12-12-2025"
        ),
        UsuarioRegistrado(
            nombre = "Lucas Quezada",
            "H2",
            fecha = "12-12-2025"
        ),
        UsuarioRegistrado(
            nombre = "Juan Astorga",
            "H2",
            fecha = "12-12-2025"
        ),
        UsuarioRegistrado(
            nombre = "Luis Ramírez",
            "H2",
            fecha = "12-12-2025"
        )
    )

    @Composable
    fun AdminHomePage(
        userVm: UserViewModel,
        residenceVm: ResidenceViewModel,
        userAppVm: UsersAppViewModel,
        onAddUser: () -> Unit,
        onSeeUsers: () -> Unit,
        onAddProperty: () -> Unit,
        onSeeProperties: () -> Unit
    ) {

        LaunchedEffect(Unit) {
            userAppVm.getUsers()
            residenceVm.getHouses()
        }

        val username by userVm.username.collectAsState("")
        val getUsersState by userAppVm.getUsersState.collectAsState()
        val getResidenceState  by residenceVm.getState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.TextPrimary)
                .padding(16.dp)
        ) {

            // ---------------- HEADER ----------------
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = "Hola ${username} 👋",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Bienvenido",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.LightGray
                )
            }

            // ---------------- LISTA SCROLL ----------------
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // ---------- DOMICILIOS ----------
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            "Domicilios Recientes",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 16.dp)

                        )
                        CustomAddIconButton(
                            "Añadir",
                            onClick = {},
                            backgroundColor = AppColors.lightBlue
                        )
                    }
                    SectionWithBackground(
                        title = "Titulo",
                        redirectLabel = "Ver todos los domicilios",
                        onRedirect = {onSeeProperties()},
                        gradient = Brush.verticalGradient(
                            listOf(
                                Color(0xFF3EDCD3),
                                Color(0xFF3AB4C3)
                            )
                        )
                    ) {
                        when(getResidenceState){
                            is ResidenceGetState.Loading -> {
                                CircularProgressIndicator(color = Color.White)
                            }

                            is ResidenceGetState.Success -> {
                                val list = getResidenceState as ResidenceGetState.Success
                                val residences = list.residence

                                residences.take(12).forEach { residences ->
                                    CasaCardDark(residences)
                                }
                            }
                            else -> Unit
                        }
                    }
                }

                // ---------- USUARIOS ----------
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Usuarios añadidos",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )

                        CustomAddIconButton(
                            "Añadir",
                            onClick = {onAddUser()},
                            backgroundColor = AppColors.lightGreen
                        )
                    }
                }

                item {
                    SectionWithBackground(
                        title = "",
                        onRedirect = {onSeeUsers()},
                        redirectLabel = "Ver todos los usuarios",
                        gradient = Brush.verticalGradient(
                            listOf(
                                Color(0xFF6FEAAD),
                                Color(0xFF208B52)
                            )
                        )
                    ) {
                        when(getUsersState){
                            is GetUsersState.Loading -> {
                                CircularProgressIndicator(color = Color.White)
                            }
                            is GetUsersState.Success -> {
                                val success = getUsersState as GetUsersState.Success
                                val users = success.users
                                users.take(10).forEach{ user ->
                                    UsuarioCardDark(user)
                                }
                            }
                            else -> Unit
                        }
                    }
                }

                // ---------- LOGOUT ----------
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        LogoutButton(
                            label = "Cerrar sesión",
                            onLogout = { userVm.logoutUser() }
                        )
                    }
                }
            }
        }
    }


    // ----------------------------
    // CARD DE DOMICILIO
    // ----------------------------
    @Composable
    fun CasaCardDark(casa: ResidenceResponse) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = AppColors.TextPrimary   // Tu color gris/oscuro
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(28.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Casa",
                    tint = AppColors.Primary,
                    modifier = Modifier.size(40.dp)
                )

                Spacer(Modifier.width(16.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        text = casa.identifier,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Dueño:",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    val texto = if(casa.residents.size > 0){
                        "Residentes: ${casa.residents.size}"
                    }else{
                        "Sin residentes registrados"
                    }
                    Text(
                        text = "${texto}",
                        color = AppColors.Primary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "${formatFecha(casa.created_at)}",
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
    fun UsuarioCardDark(usuario: AppUsers) {

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.TextPrimary
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Usuario",
                        tint = AppColors.Primary,
                        modifier = Modifier.size(42.dp)
                    )

                    Spacer(Modifier.width(16.dp))

                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "${usuario.first_name} ${usuario.last_name}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Text(
                            text = "H4",
                            fontSize = 14.sp,
                            color = Color.LightGray
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Creado",
                            color = AppColors.Primary,
                            fontSize = 13.sp
                        )
                        Text(
                            text = usuario.email,
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // ------- BADGE DE ROL -------
            Badge(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = -12.dp, y = 0.dp),
                containerColor = AppColors.Primary,
                contentColor = Color.Black
            ) {
                Text(
                    text = "Usuario",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
