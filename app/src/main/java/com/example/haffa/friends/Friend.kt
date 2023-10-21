package com.example.haffa.friends

import com.example.haffa.routes.Route
import java.io.Serializable

data class Friend(
    var imgUrl: String,
    var name: String,
    var phone: String,
    val routes: List<Route>
) : Serializable