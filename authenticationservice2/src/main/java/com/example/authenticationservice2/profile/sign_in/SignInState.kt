package com.example.authenticationservice2.profile


data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)