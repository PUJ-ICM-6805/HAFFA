package com.example.haffa.friends

import com.example.haffa.model.Route
import java.io.Serializable

data class Friend(
    var imgUrl: String,
    var name: String,
    var phone: String,
    var routes: List<Route>?
) : Serializable