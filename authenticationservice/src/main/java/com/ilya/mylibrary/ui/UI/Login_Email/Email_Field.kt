package com.ilya.mylibrary
import com.ilya.mylibrary.R

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp




@RequiresApi( Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Email_field(
    modifier: Modifier = Modifier,
    font: FontFamily,
    onTextReturn: (String) -> Unit // Callback для возврата текста
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var email by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) } // Состояние фокуса
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
            .background(background_color) // Используем цвет поверхности Material 3
            .padding(horizontal = 30.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Текст, который будет над полем ввода
        Text(
            text = stringResource(id = R.string.email),
            fontSize = 20.sp,
            color = text, // Цвет текста зависит от темы
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = font
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Поле ввода с использованием Material 3 TextField
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Введите email", style = TextStyle(fontFamily = font)) },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState -> isFocused = focusState.isFocused }, // Отслеживаем фокус
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
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide() // Скрываем клавиатуру
                    if (email.isNotEmpty()) {
                        onTextReturn(email) // Возвращаем текст через callback
                    }
                }
            ),
            singleLine = true // Однострочное поле ввода
        )
    }
}

