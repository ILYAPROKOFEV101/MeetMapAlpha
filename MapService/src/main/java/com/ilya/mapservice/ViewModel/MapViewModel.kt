package com.ilya.mapservice.ViewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf


class MapViewModel : ViewModel() {
    // Маркеры
    private val _markers = mutableStateListOf<Point>()
    val markers: List<Point> get() = _markers

    // Маршрут
    private val _route = mutableStateListOf<Point>()
    val route: List<Point> get() = _route

    // Позиция камеры (исправлены типы)
    private val _cameraPosition = mutableStateOf(
        CameraPosition(
            Point(55.751574, 37.573856),  // Широта, долгота
            11.0F,  // Zoom (Double)
            0.0F,   // Azimuth (Double)
            0.0F    // Tilt (Double)
        )
    )
    val cameraPosition: State<CameraPosition> = _cameraPosition

    fun addMarker(point: Point) {
        _markers.add(point)
    }

    fun setRoute(points: List<Point>) {
        _route.clear()
        _route.addAll(points)
    }

    fun moveCamera(position: CameraPosition) {
        _cameraPosition.value = position
    }
}