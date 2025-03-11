package com.example.authenticationservice2.profile.ui.UI

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import com.example.authenticationservice2.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonAppBar(navController: NavController) {

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color(0xFF191C20) else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val selectedIconColor = if (isDarkTheme) Color.White else Color.Black
    val unselectedIconColor = if (isDarkTheme) Color.Gray else Color.DarkGray

    NavigationBar(
        modifier = Modifier.wrapContentSize(),
        containerColor = backgroundColor // Динамический фон
    ) {
        val items = listOf(
            BottomNavigationItem(
                title = stringResource(id = R.string.logwithemail),
                selectedIcon = Icons.Filled.Email,
                unselectedIcon = Icons.Outlined.Email,
                hasNews = false,
            )
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->
            selectedItemIndex = when (destination.route) {
                "admin_fragment" -> 0
                else -> selectedItemIndex
            }
        }
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    when (index) {
                        0 -> navController.navigate("login")
                    }
                },
                label = {
                    Text(text = item.title, fontSize = 20.sp, color = textColor)
                },
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedIconColor, // Цвет выбранного значка
                    unselectedIconColor = unselectedIconColor, // Цвет невыбранного значка
                    selectedTextColor = textColor, // Цвет текста
                    unselectedTextColor = unselectedIconColor, // Цвет невыбранного текста
                    indicatorColor = backgroundColor // Цвет фона кнопки
                )
            )
        }
    }
}