package com.example.residente_app.ui.components.layouts.User

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FloatingBottomNavBar(
    items: List<UserNavItem>,
    modifier: Modifier = Modifier,
    currentRoute: String?,
    onNavigate: (route:String) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(
                    color = Color(0xFF1C1C1C),
                    shape = RoundedCornerShape(40.dp)
                )
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            items.forEach { item ->
                val isSelected = currentRoute == item.route

                BottomNavItem(
                    item = item,
                    isSelected = isSelected,
                    onClick = { onNavigate(item.route)}
                )
            }
        }
    }
}

@Composable
fun BottomNavItem(
    item: UserNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val background = if (isSelected) Color(0xFFFFA629) else Color.Transparent
    val contentColor = if (isSelected) Color.Black else Color.White

    Row(
        modifier = Modifier
            .height(50.dp)
            .background(background, RoundedCornerShape(30.dp))
            .padding(horizontal = if (isSelected) 18.dp else 12.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            tint = contentColor
        )

        if (isSelected) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item.label,
                color = contentColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}