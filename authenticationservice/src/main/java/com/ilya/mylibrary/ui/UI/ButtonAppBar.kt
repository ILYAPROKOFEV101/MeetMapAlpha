package com.ilya.mylibrary
import android.graphics.fonts.FontFamily
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.ilya.mylibrary.R

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonAppBar(
    navController: NavController,
    modifier: Modifier,
    font: androidx.compose.ui.text.font.FontFamily,
) {

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color(0xFF191C20) else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val selectedIconColor = if (isDarkTheme) Color.White else Color.Black
    val unselectedIconColor = if (isDarkTheme) Color.Gray else Color.DarkGray

    Column(Modifier
        .clickable{
            navController.navigate("login")
        }
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {
                navController.navigate("login")
            },
            modifier = modifier
                .size(56.dp) // Размер по Material Guidelines
                .clip(CircleShape)
                .background(backgroundColor), // Фон для контраста
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = textColor
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = stringResource(R.string.logwithemail),
                modifier = Modifier.size(36.dp) // Стандартный размер иконки
            )
        }
        Spacer(Modifier.height(10.dp).fillMaxWidth())
        Text(
            text = stringResource(id = R.string.logwithemail),
            fontSize = 20.sp,
            color = textColor, // замените textColor на конкретный цвет, если нужно
            textAlign = TextAlign.Center,
            fontFamily = font,
            fontWeight = FontWeight.SemiBold,

        )
    }


}