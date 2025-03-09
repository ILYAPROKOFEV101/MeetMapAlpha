package com.yourmood.yourmood.ui.UI.Login_Email

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.res.painterResource


import com.example.authenticationservice.R  // Замените на ваш package

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    font: FontFamily,
    onTextReturn: (String) -> Unit // Callback для возврата текста
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) } // Состояние видимости пароля
    var passwordError by remember { mutableStateOf(false) }

    val background_color = if (isSystemInDarkTheme())
        Color(0xFF191C20)
    else Color(0xFFFFFFFF)
    val text = if (isSystemInDarkTheme())
        Color(0xFFFFFFFF)
    else
        Color(0xFF191C20)


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_color) // Используем цвет поверхности Material 3 <button class="citation-flag" data-index="1">
            .padding(horizontal = 30.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Текст, который будет над полем ввода
        Text(
            text = stringResource(id = R.string.password),
            fontSize = 20.sp,
            color = text, // Цвет текста зависит от темы <button class="citation-flag" data-index="7">
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = font
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Поле ввода с использованием Material 3 OutlinedTextField
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = it.length < 6 // Проверяем длину пароля
            },
            label = { Text(text = "Введите пароль", style = TextStyle(fontFamily = font)) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 20.sp, fontFamily = font),

            colors = TextFieldDefaults.colors(
                focusedTextColor = text, // Цвет текста при фокусе
                unfocusedTextColor = text, // Цвет текста без фокуса
                focusedLabelColor = text, // Цвет метки при фокусе
                unfocusedLabelColor = text, // Цвет метки без фокуса
                focusedContainerColor = background_color, // Цвет фона при фокусе
                unfocusedContainerColor = background_color, // Цвет фона без фокуса
                focusedIndicatorColor = text, // Цвет рамки при фокусе
                unfocusedIndicatorColor = Color.Transparent, // Прозрачная рамка без фокуса
                cursorColor = text, // Цвет курсора
                focusedPlaceholderColor = text, // Цвет плейсхолдера при фокусе
                unfocusedPlaceholderColor = text // Цвет плейсхолдера без фокуса
            ),
            
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(), // Отображение/скрытие пароля
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painter = if (isPasswordVisible) painterResource(id = R.drawable.visibility_24px) else painterResource(id = R.drawable.visibility_off_24px),
                        contentDescription = if (isPasswordVisible) "Скрыть пароль" else "Показать пароль",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            isError = passwordError,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide() // Скрываем клавиатуру
                    if (password.isNotEmpty()) {
                        onTextReturn(password) // Возвращаем текст через callback
                    }
                }
            ),
            singleLine = true // Однострочное поле ввода
        )

        if (passwordError) {
            Text(
                text = "Пароль должен содержать минимум 6 символов",
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}