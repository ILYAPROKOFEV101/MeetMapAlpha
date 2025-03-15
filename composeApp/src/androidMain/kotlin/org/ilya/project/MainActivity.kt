package org.ilya.project

import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.compose.ComposeGoogleSignInCleanArchitectureTheme
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.ilya.mapservice.ViewModel.MapsActivity
import com.ilya.mylibrary.AuthNavGraph
import com.ilya.mylibrary.GoogleAuthUiClient
import com.ilya.mylibrary.NavGraphMain
import com.ilya.mylibrary.Novigation.Destinations
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.mapview.MapView


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

    private lateinit var mapView: MapView





    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
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



                    /*AuthNavGraph(
                        navController = navController,
                        googleAuthUiClient = googleAuthUiClient,
                        activity = this
                    )*/

                    if (ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        val intent = Intent(this@MainActivity, MapsActivity::class.java)
                        startActivity(intent)
                    } else {
                        // Запросить разрешение на доступ к местоположению
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                            1
                        )
                    }

                }
            }
        }
    }
}


