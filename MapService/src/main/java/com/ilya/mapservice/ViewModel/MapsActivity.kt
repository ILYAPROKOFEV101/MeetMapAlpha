package com.ilya.mapservice.ViewModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import com.ilya.mapservice.R
import com.ilya.mapservice.databinding.ActivityMapsBinding
import com.yandex.mapkit.MapKitFactory

class MapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setApiKey(savedInstanceState)
        MapKitFactory.setApiKey("c48e8c80-6757-49c0-902b-564357bc0792")
        MapKitFactory.initialize(this)
        // Исправлено на ActivityMapsBinding
        binding = ActivityMapsBinding.inflate(layoutInflater) // ✅ Правильный Binding
        setContentView(binding.root)
    }

    private fun setApiKey(savedInstanceState: Bundle?) {
        val haveApiKey = savedInstanceState?.getBoolean("haveApiKey") ?: false // При первом запуске приложения всегда false
        if (!haveApiKey) {
            MapKitFactory.setApiKey(MAPKIT_API_KEY) // API-ключ должен быть задан единожды перед инициализацией MapKitFactory
        }
    }

    // Если Activity уничтожается (например, при нехватке памяти или при повороте экрана) - сохраняем информацию, что API-ключ уже был получен ранее
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("haveApiKey", true)
    }

    // Отображаем карты перед моментом, когда активити с картой станет видимой пользователю:
    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
    }

    // Останавливаем обработку карты, когда активити с картой становится невидимым для пользователя:
    override fun onStop() {
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }


    companion object {
        const val MAPKIT_API_KEY = "c48e8c80-6757-49c0-902b-564357bc0792"
    }
}