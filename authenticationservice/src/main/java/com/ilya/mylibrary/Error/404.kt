package com.ilya.mylibrary.Error

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilya.mylibrary.R

@Composable
fun Wrong(
    modifier : Modifier,
    error: String,
    font: FontFamily
){
    val errorMessage = error?.toString() ?: "Unknown error"
    val text = if (isSystemInDarkTheme())
        Color(0xFFFFFFFF)
    else
        Color(0xFF191C20)

    Column(modifier.fillMaxSize()) {
        Box(
            modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
        )
        {
            Image(
                painter = painterResource(id = R.drawable.wrong),
                contentDescription = stringResource(id = R.string.wrong),
                modifier.fillMaxSize().clip(RoundedCornerShape(70.dp))
            )
        }
        Spacer(modifier.fillMaxHeight(0.1f))
        Box(
            modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)

        ){
            Text(
                color = text,
                text = errorMessage,
                textAlign = TextAlign.Center,
                fontFamily = font,
                fontSize = 24.sp,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Preview
@Composable
fun showthis(){
    val robotoBold = FontFamily(Font(R.font.roboto_bold))

    Box(modifier = Modifier)

    {
        Wrong(Modifier, "Wrong", robotoBold)
    }

}
