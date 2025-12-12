package com.example.residente_app.ui.components.search

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.residente_app.ui.theme.AppColors

@Composable
fun EmptySearchResult(
    title: String = "Sin resultados",
    description: String = "No encontramos coincidencias para tu búsqueda.",
    showButton: Boolean = false,
    buttonText: String = "Volver",
    icon: @Composable (() -> Unit)? = null,
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Ícono
        if (icon == null) {
            Icon(
                imageVector = Icons.Default.SearchOff,
                contentDescription = null,
                tint = AppColors.Primary,
                modifier = Modifier.size(90.dp)
            )
        } else {
            icon()
        }

        Spacer(modifier = Modifier.height(20.dp))

        // TÍTULO
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(10.dp))

        // DESCRIPCIÓN
        Text(
            text = description,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 20.dp),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(30.dp))

        // BOTÓN OPCIONAL
        if (showButton) {
            Button(
                onClick = onBackClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Primary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(buttonText, color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}