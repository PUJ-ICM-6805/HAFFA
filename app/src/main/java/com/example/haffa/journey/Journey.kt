package com.example.haffa.journey


import java.io.Serializable
import java.util.Date

data class Journey
    (var distance : Double,
     var route : String,
     var points : Int,
     var duration : Double,
     var imgUrl : String,
     var altitude : Int,
     var date: Date) : Serializable