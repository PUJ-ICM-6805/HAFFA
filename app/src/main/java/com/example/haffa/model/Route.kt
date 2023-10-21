package com.example.haffa.model

import android.net.Uri
import com.google.type.LatLng
import java.io.Serializable
import java.time.LocalDate

data class Route(
    var name: String = "",
    var maxAltitude: Float = 0f,
    var minAltitude: Float = 0f,
    var averageAcceleration: Float = 0f,
    var points: Int = 0,
    var distance: Double = 0.0,
    var duration: Double = 0.0,
    var localDate: String = "",
    var imgUrl: String? = null,
    var locations: List<Map<String, Double>> = listOf()
) : Serializable
