package com.ilya.mylibrary
import com.ilya.mylibrary.R


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
@Composable
fun LoadingCircle() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        // Анимация вращения
        val rotation = rememberInfiniteTransition().animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )

        // Анимация для угла дуги (создаем эффект, что круг почти замкнут)
        val sweepAngle = rememberInfiniteTransition().animateFloat(
            initialValue = 1f,  // Начальный минимальный угол
            targetValue = 359f,  // Максимальный угол
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 20000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
        // Определяем кастомные цвета для градиента
        val rainbowBrush = Brush.sweepGradient(
            colors = listOf(
                Color.Red,
                Color(0xFFFFA500),  // Оранжевый
                Color.Yellow,
                Color(0xA14557FF),
                Color(0xDA6EB7E7),  // Индиго
                Color(0xFF8A2BE2)   // Фиолетовый
            )
        )

        // Canvas для рисования кастомного кольца с градиентом
        Canvas(
            modifier = Modifier
                .size(100.dp)
                .rotate(rotation.value)  // Вращаем кольцо
        ) {
            // Рисуем дугу с градиентом, которая не будет полной
            drawArc(
                brush = rainbowBrush,
                startAngle = 0f,
                sweepAngle = sweepAngle.value,  // Анимируемый угол дуги
                useCenter = false,  // Чтобы не заполнять центр
                style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)  // Толщина и форма концов линий
            )
        }
    }
}
