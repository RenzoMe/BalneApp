package com.example.balneapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.balneapp.navigation.AppRoute
import com.example.balneapp.navigation.topLevelDestinations

@Composable
fun BalneAppBottomBar(
    selectedRoute: String,
    onDestinationSelected: (AppRoute) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = Color(0xFFFAFAFA),
        tonalElevation = 0.dp
    ) {
        topLevelDestinations.forEach { destination ->
            val selected = selectedRoute == destination.route.route
            NavigationBarItem(
                selected = selected,
                onClick = { onDestinationSelected(destination.route) },
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        if (selected) Color(0xFFCFF7FA) else Color.Transparent
                    ),
                icon = {
                    Icon(
                        painter = painterResource(destination.iconRes),
                        contentDescription = destination.label,
                        tint = Color.Unspecified
                    )
                },
                label = {
                    Text(destination.label, fontSize = 10.sp)
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF079EAD),
                    selectedTextColor = Color(0xFF079EAD),
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = Color(0xFF0B6F78),
                    unselectedTextColor = Color(0xFF777777)
                )
            )
        }
    }
}
