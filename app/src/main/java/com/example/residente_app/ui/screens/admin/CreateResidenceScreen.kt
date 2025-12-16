package com.example.residente_app.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.residente_app.viewmodel.CreateResidenceState
import com.example.residente_app.viewmodel.ResidenceViewModel

@Composable
fun CreateResidenceScreen(
    residenceVm: ResidenceViewModel,
    onBack: ()-> Unit
) {
    var identifier by remember { mutableStateOf("") }
    val createState by residenceVm.createResidenceState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .clickable { onBack() }
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Volver",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 24.dp), // padding lateral sin afectar fondo
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Crear Residencia",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            OutlinedTextField(
                value = identifier,
                onValueChange = { identifier = it },
                label = { Text("Identificador de la residencia") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Button(
                onClick = {
                    if (identifier.isNotBlank()) {
                        residenceVm.createResidence(identifier)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = createState !is CreateResidenceState.Loading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2ECC71), // verde
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF2ECC71).copy(alpha = 0.5f)
                )
            ) {
                Text("Crear")
            }

            when (createState) {
                is CreateResidenceState.Loading -> {
                    CircularProgressIndicator(color = Color.White)
                }

                is CreateResidenceState.Success -> {
                    Text(
                        text = "Residencia creada exitosamente",
                        color = Color(0xFF2ECC71)
                    )
                }

                is CreateResidenceState.Error -> {
                    Text(
                        text = (createState as CreateResidenceState.Error).message
                            ?: "Ocurrió un error",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {}
            }
        }
    }
}
