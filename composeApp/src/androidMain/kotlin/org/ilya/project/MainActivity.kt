package org.ilya.project

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.compose.ComposeGoogleSignInCleanArchitectureTheme
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.ilya.mylibrary.AuthNavGraph
import com.ilya.mylibrary.GoogleAuthUiClient
import com.ilya.mylibrary.NavGraphMain
import com.ilya.mylibrary.Novigation.Destinations
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point


class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    var leftop by mutableStateOf(true)
    var username by mutableStateOf("")
    var password by mutableStateOf("")


    var cloth by mutableStateOf(true)


    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = this,
            oneTapClient = Identity.getSignInClient(this),
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        FirebaseApp.initializeApp(this)
        MapKitFactory.setApiKey("c48e8c80-6757-49c0-902b-564357bc0792")
        setContent {
            val markers = remember {
                listOf(
                    Point(55.751574, 37.573856), // Москва
                    Point(59.934280, 30.335098)   // Санкт-Петербург
                )
            }

            val route = remember {
                listOf(
                    Point(55.751574, 37.573856),
                    Point(56.326944, 44.0075),    // Нижний Новгород
                    Point(59.934280, 30.335098)
                )
            }

            ComposeGoogleSignInCleanArchitectureTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    LaunchedEffect(Unit) {
                        if (googleAuthUiClient.getSignedInUser() != null) {
                            Log.d("Navigation", "Navigating from SIGN_IN to PROFILE")
                            navController.navigate(Destinations.PROFILE)
                        }
                    }

                    AuthNavGraph(
                        navController = navController,
                        googleAuthUiClient = googleAuthUiClient,
                        activity = this
                    )


                   /* YandexMap(
                        modifier = Modifier.fillMaxSize(),
                        markers = markers,
                        route = route,

                    )*/
                }
            }
        }
    }
}


