package org.ilya.project

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.compose.ComposeGoogleSignInCleanArchitectureTheme
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.ilya.mylibrary.AuthActivity
import com.ilya.mylibrary.AuthNavGraph
import com.ilya.mylibrary.GoogleAuthUiClient

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

        setContent {
            ComposeGoogleSignInCleanArchitectureTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AuthNavGraph(
                        navController = navController,
                        googleAuthUiClient = googleAuthUiClient,
                        activity = this
                    )
                }
            }
        }
    }
}


