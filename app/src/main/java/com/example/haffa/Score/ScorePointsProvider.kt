package com.example.myapplicationviews

class ScorePointsProvider {

    companion object {
       val scorePointsList = listOf<ScorePointData>(
           ScorePointData(10, "Miecoles 30 de septiembre"),
           ScorePointData(20, "Martes 31 de septiembre"),
           ScorePointData(30, "Miercoles 1 de octubre"),
           ScorePointData(40, "Jueves 2 de octubre"),
           ScorePointData(50, "Viernes 3 de octubre")
       )
    }
}