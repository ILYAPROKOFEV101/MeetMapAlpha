package com.ilya.mylibrary
import com.ilya.mylibrary.R

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException


fun registerUser(
    context: Context,
    auth: FirebaseAuth,
    email: String,
    password: String,
    onResult: (Boolean) -> Unit
) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Registration successful
                val user = auth.currentUser
                Log.d("Registration", "User registered successfully")
                onResult(true) // Пользователь успешно зарегистрирован
            } else {
                // Registration failed
                val exception = task.exception
                if (exception is FirebaseAuthUserCollisionException) {
                    // Пользователь уже существует, попытаемся войти
                    signInUser(context, auth, email, password, onResult)
                } else {
                    // Другая ошибка, обработаем ее
                    val message = exception?.message ?: "Unknown error"
                    Log.d("Registration", "Registration failed: $message")
                    showToast(context, "Registration failed: $message")
                    onResult(false) // Регистрация не удалась
                }
            }
        }
}
