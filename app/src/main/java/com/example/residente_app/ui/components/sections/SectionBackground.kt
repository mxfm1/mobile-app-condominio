package com.example.residente_app.ui.components.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.residente_app.ui.components.buttons.AgregarUsuariosButton
import com.example.residente_app.ui.theme.AppColors
import com.example.residente_app.ui.theme.TestColors

@Composable
fun SectionWithBackground(
    title: String,
    gradient: Brush,
    onRedirect: (() -> Unit)? = null,
    redirectLabel:String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false
            )
            .background(gradient,shape = RoundedCornerShape(20.dp))
            .padding(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            content()

            // ➤ ENCABEZADO CON LINK A LA DERECHA
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                onRedirect?.let {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Button(
                            onClick = it,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppColors.TextPrimary,
                            ),
                            modifier = Modifier
                                .padding(top=8.dp)
                                .shadow(elevation = 12.dp, clip = false, shape = RoundedCornerShape(16.dp))
                            ,

                        ) {
                            Text(
                                text = redirectLabel ?: "",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                            )
                        }
                    }
                }
            }
            // ➤ CONTENIDO DE LA SECCIÓN
        }
    }
}

