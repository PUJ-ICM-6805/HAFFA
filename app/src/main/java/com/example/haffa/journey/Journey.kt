package com.example.haffa.journey.model


import java.util.Date

data class Journey
    (var distance : Double,
     var route : String,
     var points : Int,
     var duration : Double,
     var imgUrl : String,
     var date: Date)