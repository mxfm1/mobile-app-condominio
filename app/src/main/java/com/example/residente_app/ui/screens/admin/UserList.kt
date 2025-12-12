package com.example.residente_app.ui.screens.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.residente_app.ui.components.buttons.CustomAddIconButton
import com.example.residente_app.ui.components.sections.SectionWithBackground
import com.example.residente_app.ui.theme.AppColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.residente_app.data.remote.DTO.GetUsersResponse
import com.example.residente_app.viewmodel.GetUsersState
import com.example.residente_app.viewmodel.UsersAppViewModel
import com.example.residente_app.data.remote.DTO.AppUsers
import com.example.residente_app.ui.components.EmptyData

// --- MOCK DATA ---
data class UserUiModel(
    val name: String,
    val residence: String
)

val mockUsers = listOf(
    UserUiModel("Carlos Ramírez", "A4"),
    UserUiModel("María Fernández", "A12"),
    UserUiModel("Luis González", "C3"),
    UserUiModel("Ana Martínez", "H1"),
)

@Composable
fun UserListAdminScreen(
    onBack: () -> Unit,
    vm: UsersAppViewModel,
    onUserClick:(Int) -> Unit
) {

    LaunchedEffect(Unit) {
        vm.getUsers()
    }

    val usersState by vm.getUsersState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.TextPrimary)
            .padding(16.dp)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            // ---------- HEADER ----------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Lista de usuarios",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )

                CustomAddIconButton(
                    "",
                    onClick = { onBack()},
                    icon = Icons.Default.ArrowBackIosNew,
                    backgroundColor = AppColors.TextPrimary,
                    contentColor = AppColors.Background
                )
            }

            // ---------- SUBTÍTULO ----------
            Text(
                text = "Aquí puedes ver todos los usuarios registrados en la aplicación.",
                color = Color(0xFFB0B0B0),
                fontSize = 15.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // ---------- SECCIÓN CON GRADIENTE ----------
            when(usersState){
                is GetUsersState.Loading -> {
                    CircularProgressIndicator(color = Color.White)
                }

                is GetUsersState.Error -> {
                    val error = usersState as GetUsersState.Error
                    val msg = error.message
                    Text(
                        text = msg,
                        color = Color.Red
                    )
                }

                is GetUsersState.Success -> {
                    val success = usersState as GetUsersState.Success
                    val users = success.users
                    LazyColumn {
                        if(users.isEmpty()){
                            item{
                                EmptyData()
                            }
                        }else{
                            items(users){user ->
                                UserCard(user, onItemClick = {id-> onUserClick(id)} )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }

                else -> Unit
            }
        }
    }
}

@Composable
fun UserCard(
    user: AppUsers,
    onItemClick: (Int) -> Unit
) {
    Button(
        onClick = {onItemClick(user.id)},
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.DarkGranate
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ){
            Icon(
                Icons.Default.Person,
                contentDescription = "user",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "${user.first_name} ${user.last_name}",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Sin residencia asignada",
                    color = Color(0xFFB5B5B5),
                    fontSize = 14.sp
                )
            }
        }
    }
}
