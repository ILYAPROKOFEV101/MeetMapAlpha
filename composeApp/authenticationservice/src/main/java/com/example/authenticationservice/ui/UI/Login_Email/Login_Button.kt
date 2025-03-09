package com.yourmood.yourmood.ui.UI.Login_Email


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
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
import com.google.firebase.auth.FirebaseAuth
import registerUser
import com.example.authenticationservice.R  // Замените на ваш package


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    modifier: Modifier = Modifier,
    fontFamily: FontFamily,
    email: String,
    password: String,
    context: Context,
    auth: FirebaseAuth
): Boolean {
    var isLoading by remember { mutableStateOf(false) } // Состояние загрузки

    Button(
        onClick = {
            if (email.isNotBlank() && password.isNotBlank()) {
                isLoading = true // Показываем индикатор загрузки
                registerUser(context, auth, email, password) { success ->
                    isLoading = false // Скрываем индикатор загрузки
                    if (!success) {
                        // Обработка ошибки регистрации
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