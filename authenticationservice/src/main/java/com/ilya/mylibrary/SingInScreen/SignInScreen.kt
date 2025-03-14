package com.ilya.mylibrary.SingInScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ilya.mylibrary.Email_SingIn

import com.ilya.mylibrary.Novigation.Destinations
import com.ilya.mylibrary.rememberFirebaseAuthLauncher
import com.ilya.mylibrary.R


@Composable
    fun SignInScreen(
        screen: Destinations,
        onSignInClick: () -> Unit,
        navController: NavController
    ) {
    val robotoBold = FontFamily(
        Font(R.font.roboto_bold) // Убедитесь, что имя файла правильное и соответствует переименованному файлу
    )
        var user by remember { mutableStateOf(Firebase.auth.currentUser) }
        val launcher = rememberFirebaseAuthLauncher(
            onAuthComplete = { result ->
                user = result.user
                navController.navigate(screen.PROFILE)
            },
            onAuthError = {
                user = null
                navController.navigate(screen.ERROR)
            }
        )
        val token = stringResource(id = R.string.web_client_id)
        val context = LocalContext.current


        val isDarkTheme = isSystemInDarkTheme()
        val backgroundColor = if (isDarkTheme) Color(0xFF191C20) else Color.White
        val textColor = if (isDarkTheme) Color.White else Color.Black

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(token)
        .requestEmail()
        .build()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {

            if (user == null) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(backgroundColor),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(
                            onClick = {
                                Log.d("GoogleSignIn", "Attempting to sign in")

                                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                                launcher.launch(googleSignInClient.signInIntent)
                                Log.d("GoogleSignIn", "Sign in intent launched")
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.google),
                                contentDescription = "Nothing",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            stringResource(id = R.string.login),
                            fontSize = 20.sp,
                            color = textColor, // замените textColor на конкретный цвет, если нужно
                            textAlign = TextAlign.Center,
                            fontFamily = robotoBold,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable
                            {
                                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                                launcher.launch(googleSignInClient.signInIntent)
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(backgroundColor)
                        ) {
                            Email_SingIn(navController, Modifier, robotoBold)
                        }
                    }
                }
            }
        }
