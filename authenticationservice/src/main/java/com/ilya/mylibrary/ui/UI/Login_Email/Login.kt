package com.ilya.mylibrary
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.google.firebase.auth.FirebaseAuth
import com.ilya.mylibrary.Novigation.Destinations


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    modifier: Modifier = Modifier,
    fontFamily: FontFamily,
    email: String,
    password: String,
    context: Context,
    auth: FirebaseAuth,
    navController: NavController,
): Boolean {
    var isLoading by remember { mutableStateOf(false) } // Состояние загрузки
    val screen  = Destinations
    Button(
        onClick = {
            if (email.isNotBlank() && password.isNotBlank()) {
                isLoading = true // Показываем индикатор загрузки
                registerUser(context, auth, email, password) { success ->
                    navController.navigate(screen.PROFILE)

                    isLoading = false // Скрываем индикатор загрузки
                    if (!success) {
                        // Обработка ошибки регистрации
                        navController.navigate(screen.ERROR)
                        Toast.makeText(context, "Ошибка регистрации", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF3998FF), // Цвет фона кнопки
            contentColor = Color.White // Цвет текста
        ),
        shape = RoundedCornerShape(20.dp), // Закругление углов
        enabled = !isLoading // Блокируем кнопку во время загрузки
    ) {
        if (isLoading) {
            CircularProgressIndicator( // Индикатор загрузки
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = stringResource(id = R.string.singin),
                fontSize = 20.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold
            )
        }
    }

    return true
}