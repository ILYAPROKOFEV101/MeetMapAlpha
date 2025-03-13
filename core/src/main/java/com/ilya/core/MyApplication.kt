package com.ilya.core

import android.app.Application
import com.google.firebase.FirebaseApp

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Инициализация Firebase
        FirebaseApp.initializeApp(this)
    }
}