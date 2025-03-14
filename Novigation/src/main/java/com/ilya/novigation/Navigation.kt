package com.ilya.mylibrary

import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ilya.novigation.DestinationsMain


// Navigation.kt


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraphMain(
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient,
    activity: ComponentActivity,
    modifier: Modifier = Modifier
) {
    // Логирование текущего экрана при изменении маршрута
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            val currentScreen = backStackEntry.destination.route
            Log.d("Navigation", "Current screen: $currentScreen")
        }
    }

    NavHost(
        navController = navController,
        startDestination = DestinationsMain.AUTH,
        modifier = modifier
    ) {


        composable(DestinationsMain.MAP) {
            // Пустой экран для карты
        }
    }
}