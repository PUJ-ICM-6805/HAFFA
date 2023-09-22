package com.example.haffa.routes

import java.io.Serializable
import java.util.Date

data class Route(
    var distance: Double,
    var name: String,
    var points: Int,
    var duration: Double,
    var imgUrl: String,
    var altitude: Int,
    var date: Date
) : Serializable