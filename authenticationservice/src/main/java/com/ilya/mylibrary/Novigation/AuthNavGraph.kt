package com.ilya.mylibrary

import android.app.Activity
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ilya.mylibrary.Novigation.Destinations

import kotlinx.coroutines.launch
import com.ilya.mylibrary.sign_in.SignInScreen
import com.ilya.mylibrary.sign_in.Wrong


// Navigation.kt


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuthNavGraph(
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient,
    activity: ComponentActivity,
    modifier: Modifier = Modifier
) {
    val robotoBold = FontFamily(Font(R.font.roboto_bold))

    // Логирование текущего экрана при изменении маршрута
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            val currentScreen = backStackEntry.destination.route
            Log.d("Navigation", "Current screen: $currentScreen")
        }
    }

    NavHost(
        navController = navController,
        startDestination = Destinations.SIGN_IN,
        modifier = modifier
    ) {
        composable(Destinations.SIGN_IN) {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                if (googleAuthUiClient.getSignedInUser() != null) {
                    Log.d("Navigation", "Navigating from SIGN_IN to PROFILE")
                    navController.navigate(Destinations.PROFILE)
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        activity.lifecycleScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                        Log.d("Navigation", "Navigating from SIGN_IN to PROFILE after successful sign-in")
                       navController.navigate(Destinations.PROFILE)
                    }
                }
            )

            LaunchedEffect(state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(
                        activity,
                        "Регистрация прошла успешно",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("Navigation", "Navigating from SIGN_IN to PROFILE after successful registration")
                    navController.navigate(Destinations.PROFILE)
                    viewModel.resetState()
                }
            }

            SignInScreen(
                Destinations,
                onSignInClick = {
                    activity.lifecycleScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                },
                navController = navController,
            )


        }

        composable(Destinations.PROFILE) {
            Log.d("Navigation", "Entered PROFILE screen")
            ProfileScreen(
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    activity.lifecycleScope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(
                            activity,
                            "Goodbye",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("Navigation", "Navigating back from PROFILE to SIGN_IN after sign-out")
                        navController.popBackStack()
                    }
                },
                navController = navController
            )
        }

        composable(Destinations.LOGIN) {
            Log.d("Navigation", "Entered LOGIN screen")
            LoginUsermenu(
                modifier = Modifier,
                robotoBold,
                activity,
                navController = navController
            )
        }

        composable(Destinations.ERROR) {
            Wrong(Modifier.fillMaxSize(), stringResource(R.string.wrong), robotoBold)
        }

        composable(Destinations.LOADING){
            LoadingCircle()
        }
    }
}