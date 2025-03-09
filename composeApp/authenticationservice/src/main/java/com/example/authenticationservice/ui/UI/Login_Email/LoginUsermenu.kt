package com.yourmood.yourmood.ui.UI.Login_Email

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.google.firebase.auth.FirebaseAuth
import com.example.authenticationservice.R  // Замените на ваш package


@Composable
fun LoginUsermenu(
    modifier: Modifier = Modifier,
    fontFamily: FontFamily,
    context: Context,

) {
    val auth = FirebaseAuth.getInstance()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val background_color = if (isSystemInDarkTheme())
        Color(0xFF191C20)
    else Color(0xFFFFFFFF)
    val text = if (isSystemInDarkTheme())
     Color(0xFFFFFFFF)
    else
        Color(0xFF191C20)


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(background_color), // Используем цвет фона из Material 3 <button class="citation-flag" data-index="1">
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .background(background_color)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.singin),
                fontSize = 35.sp,
                color = text, // Цвет текста зависит от темы <button class="citation-flag" data-index="7">
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(60.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(background_color)
                    .clip(RoundedCornerShape(20.dp))
                    .height(600.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface // Цвет карточки <button class="citation-flag" data-index="1">
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(background_color),
                    verticalArrangement = Arrangement.Center
                ) {
                    LazyColumn(

                        modifier = Modifier
                            .fillMaxSize()

                            .background(background_color),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Email_field(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(background_color)
                                ,
                                font = fontFamily,
                                onTextReturn = { enteredEmail ->
                                    email = enteredEmail // Сохраняем введённый email
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp).background(background_color))
                        }
                        item {
                            PasswordField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(background_color)
                                ,
                                font = fontFamily,
                                onTextReturn = { enteredPassword ->
                                    password = enteredPassword // Сохраняем введённый пароль
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp).background(background_color))
                        }
                        item {
                            Login(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                                    .background(background_color)
                                    .height(60.dp),
                                fontFamily = fontFamily,
                                email = email,
                                password = password,
                                auth = auth,
                                context = context
                            )
                        }
                    }
                }
            }
        }
    }
}