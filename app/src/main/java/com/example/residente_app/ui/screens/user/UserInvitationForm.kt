package com.example.residente_app.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreateInviteScreen(
    onScreenBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            HeaderSection(
                onFormBack = {onScreenBack()}
            )

            Spacer(modifier = Modifier.height(32.dp))

            InviteFormSection()
        }

        CreateInviteButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
        )
    }
}

@Composable
private fun HeaderSection(
    onFormBack: ()-> Unit
) {
    Button(
        onClick = {onFormBack()},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1C6C68),
            contentColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Volver",
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
private fun InviteFormSection() {
    Column {

        FormLabel("Nombre invitado")
        MockInput("John Doe")

        Spacer(modifier = Modifier.height(20.dp))

        FormLabel("Motivo")
        MockDropdown("Seleccionar motivo")

        Spacer(modifier = Modifier.height(24.dp))

        FormLabel("Válido hasta")
        DurationSelector()

        Spacer(modifier = Modifier.height(24.dp))

        FormLabel("Información adicional")
        MockTextArea("Patente del auto, peatón, etc")
    }
}

@Composable
private fun FormLabel(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 14.sp,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
private fun MockInput(value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(
                width = 1.dp,
                color = Color(0xFF2EA8FF),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(value, color = Color.Gray)
    }
}

@Composable
private fun MockDropdown(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color(0xFF1A1A1A), RoundedCornerShape(6.dp))
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, color = Color.Gray)
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
private fun DurationSelector() {
    Row {
        DurationChip("30 mins", selected = true)
        Spacer(modifier = Modifier.width(8.dp))
        DurationChip("1 hr")
        Spacer(modifier = Modifier.width(8.dp))
        DurationChip("2 hrs")
        Spacer(modifier = Modifier.width(8.dp))
        DurationChip("4 hrs")
    }

    Spacer(modifier = Modifier.height(10.dp))

    MockInput("Cantidad hrs")
}

@Composable
private fun DurationChip(
    text: String,
    selected: Boolean = false
) {
    Box(
        modifier = Modifier
            .background(
                if (selected) Color(0xFF1C6C68) else Color(0xFF1A1A1A),
                RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun MockTextArea(placeholder: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color(0xFF1A1A1A), RoundedCornerShape(10.dp))
            .padding(12.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = placeholder,
            color = Color.Gray,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun CreateInviteButton(
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {},
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1C6C68)
        )
    ) {
        Text(
            text = "Crear invitación",
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

