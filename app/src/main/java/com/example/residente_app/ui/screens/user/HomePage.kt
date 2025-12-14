package com.example.residente_app.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.residente_app.ui.components.buttons.LogoutButton
import com.example.residente_app.ui.screens.user.components.userHomePage.MyHouseSection
import com.example.residente_app.ui.screens.user.components.userHomePage.RecentAccessList
import com.example.residente_app.ui.screens.user.components.userHomePage.UserInfoCard
import com.example.residente_app.ui.screens.user.components.userHomePage.WelcomeHeader
import com.example.residente_app.ui.theme.AppColors
import com.example.residente_app.viewmodel.UserViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserHomePage(){
    LazyColumn(
        modifier = Modifier.padding(24.dp)
            .fillMaxWidth()
    ) {
        item {
            WelcomeHeader(
                "Felipe"
            )
            Spacer(modifier = Modifier.height(20.dp))
            UserInfoCard()
        }
        item{
            Spacer(modifier = Modifier.height(25.dp))
            MyHouseSection(
                "Carlos Carrasco",
                houseId = "F2",
                3,

            ) { }
        }
        item{
            Spacer(modifier = Modifier.height(25.dp))
            Text("Ultimos Accesos",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            RecentAccessList()
        }
    }
}