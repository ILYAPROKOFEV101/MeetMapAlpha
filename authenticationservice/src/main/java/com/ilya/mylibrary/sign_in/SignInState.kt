package com.ilya.mylibrary
import com.ilya.mylibrary.R

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)