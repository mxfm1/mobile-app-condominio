package com.example.residente_app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.residente_app.ui.theme.Typography

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val TextColor = Color(color = 0xFF1D1617)
val Primary = Color(color = 0xFF92A3FD)
val Secondary = Color(color = 0xFF9DCEFF)
val accentColor = Color(color = 0xFFC58BF2)
val grayColor = Color(color= 0xFF7B6F72)
val bgColor = Color(color = 0xFFF7F8f8)

object AppColors {
    val Primary = Color(0xFF1577E3)
    val PrimaryVariant = Color(0xFFE4C9C0)
    val Accent = Color(0xFFF7EFE8)

    val lightBlue = Color(0xFF3EDCD3)
    val lightGranate = Color(0xFF6A0F1A)
    val lightGreen= Color(0xFF6FEAAD)
    val DarkGreen = Color(0xFF208B52)
    val DarkBlue = Color(0xFF3AB4C3)
    val DarkGranate = Color(0xFF9E1B32)
    val Background = Color.White
    val Surface = Color(0xFFF9F9F9)

    val TextPrimary = Color(0xFF2C2C2C)
    val TextButtonPrimary = Color.White
    val TextSecondary = Color(0xFF6F6F6F)

    // mood colors
    val Happy = Color(0xFFC4DFA0)
    val Angry = Color(0xFFE8A19B)
    val Neutral = Color(0xFFF7E28A)
    val Sad = Color(0xFFA6C6E7)
    val Calm = Color(0xFFA7C0A2)
}

object TestColors {
    val BlueLightest = Color(0xFFA6D8FF)
    val BlueLight = Color(0xFF7ACBFF)
    val BlueMedium = Color(0xFF47B4FF)

    val BluePrimary = Color(0xFF1E90FF)
    val BluePrimaryDark = Color(0xFF1577E3)

    val NavyDark = Color(0xFF0A1A3C)
    val NavyMedium = Color(0xFF0F274D)

    val BackgroundLight = Color(0xFFF8FAFF)
    val TextSecondary = Color(0xFF8A93A6)
    val BorderLight = Color(0xFFE1E6F2)
}

@Composable
fun AppTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = AppColors.Primary,
            secondary = AppColors.Accent,
            background = AppColors.Background,
            surface = AppColors.Surface,
            onPrimary = AppColors.TextPrimary,
            onBackground = AppColors.TextPrimary,
        ),
        typography = Typography(
            headlineLarge = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppColors.TextPrimary
            ),
            bodyMedium = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = AppColors.TextSecondary
            )
        ),
        content = content
    )
}