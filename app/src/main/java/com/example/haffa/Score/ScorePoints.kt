package com.example.myapplicationviews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haffa.R

class ScorePoints : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScorePointsProvider.scorePointsList
        setContentView(R.layout.score_points)
        val btnChallengeIcon = findViewById<ImageButton>(R.id.ibChallengeIcon)


        initComponents()
        btnChallengeIcon.setOnClickListener { navigateToChallengeView() }
    }

    fun navigateToChallengeView(){
        val intent = Intent(this, ScoreChallenge::class.java)
        startActivity(intent)
    }

    fun initComponents(){
        val recyclerView = findViewById<RecyclerView>(R.id.rvScoreByDate)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ScorePointAdapter(ScorePointsProvider.scorePointsList)

    }
}