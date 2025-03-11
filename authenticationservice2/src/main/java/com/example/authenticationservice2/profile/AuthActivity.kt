package com.example.authenticationservice2.profile


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.ComposeGoogleSignInCleanArchitectureTheme
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

import com.example.authenticationservice2.R
import com.example.authenticationservice2.profile.ui.UI.ButtonAppBar
import com.example.authenticationservice2.profile.ui.UI.LoadingCircle
import com.example.authenticationservice2.profile.ui.UI.Login_Email.LoginUsermenu
import com.example.authenticationservice2.profile.ui.UI.rememberFirebaseAuthLauncher


class AuthActivity : ComponentActivity() {


    private lateinit var auth: FirebaseAuth
    var leftop by mutableStateOf(true)
    var username by mutableStateOf("")
    var password by mutableStateOf("")


    var cloth by mutableStateOf(true)

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeGoogleSignInCleanArchitectureTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if (googleAuthUiClient.getSignedInUser() != null) {
                                    navController.navigate("profile")
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Регистрация прошла успешно",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    navController.navigate("profile")
                                    viewModel.resetState()
                                    leftop = !leftop
                                }
                            }

                            SignInScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                },
                                navController
                            )
                        }


                        composable("profile") {
                            Column(modifier = Modifier.fillMaxSize()) {
                                ProfileScreen(
                                    userData = googleAuthUiClient.getSignedInUser(),
                                    onSignOut = {
                                        lifecycleScope.launch {
                                            googleAuthUiClient.signOut()
                                            Toast.makeText(
                                                applicationContext,
                                                "Goodbye",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            navController.popBackStack()
                                        }
                                    }
                                )
                                backtomenu()
                            }
                        }


                        composable("login")
                        {
                            LoginUsermenu( modifier = Modifier, robotoBold, this@AuthActivity)
                        }

                    }
                }
            }
        }
    }

    val robotoBold = FontFamily(
        Font(R.font.roboto_bold) // Убедитесь, что имя файла правильное и соответствует переименованному файлу
    )
    @Composable
    fun SignInScreen(
        state: SignInState,
        onSignInClick: () -> Unit,
        navController: NavController
    ) {
        var unvisible by remember { mutableStateOf(false) }
        var user by remember { mutableStateOf(Firebase.auth.currentUser) }
        val launcher = rememberFirebaseAuthLauncher(
            onAuthComplete = { result ->
                user = result.user
            },
            onAuthError = {
                user = null
            }
        )
        val token = stringResource(id = R.string.web_client_id)
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val serverSetting = remember { mutableStateOf(false) }

        val isDarkTheme = isSystemInDarkTheme()
        val backgroundColor = if (isDarkTheme) Color(0xFF191C20) else Color.White
        val textColor = if (isDarkTheme) Color.White else Color.Black

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            if (!leftop) {
                toshear(userData = googleAuthUiClient.getSignedInUser())
                Box(
                    modifier = Modifier
                        .height(400.dp)
                        .align(Alignment.Center)
                        .padding(top = 100.dp)
                ) {
                    LoadingCircle()
                }
            }
            if (user == null) {
                if (!unvisible) {
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
                                val gso =
                                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .requestIdToken(token)
                                        .requestEmail()
                                        .build()
                                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                                launcher.launch(googleSignInClient.signInIntent)
                                leftop = !leftop
                                unvisible = !unvisible

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
                            fontFamily = robotoBold,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(backgroundColor)
                        ) {
                            ButtonAppBar(navController)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun toshear(userData: UserData?) {
        if (userData?.username != null) {
            //  val intent = Intent(this@MainActivity, Map_Activity::class.java)
            // startActivity(intent)
        }
    }

    @Composable
    fun backtomenu() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.BottomEnd // Размещаем Box внизу

        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 70.dp, end = 70.dp)
                    .height(50.dp)
                    .align(Alignment.Center),
                colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50)),
                shape = RoundedCornerShape(20.dp),
                onClick = {
                    //  val intent = Intent(this@MainActivity, Map_Activity::class.java)
                    //   startActivity(intent)
                    //   finish()
                }
            )
            {
                Text(stringResource(id = R.string.back))
            }
        }
    }














}

