package com.example.myapplicationviews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haffa.R

class ScoreChallenge : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScoreChallengeProvider.scoreChallengeList
        setContentView(R.layout.score_challenge)
        val btnScoreIcon = findViewById<ImageButton>(R.id.ibScoreIcon)

        initComponents()

        btnScoreIcon.setOnClickListener { navigateToScoreView() }
    }

    fun navigateToScoreView(){
        val intent = Intent(this, ScorePoints::class.java)
        startActivity(intent)
    }

    fun initComponents() {
        val recyclerView = findViewById<RecyclerView>(R.id.rvScoreChallenge)
        val manager = GridLayoutManager(this, 2) // Configura el GridLayoutManager con 2 columnas
        recyclerView.layoutManager = manager // Asigna el GridLayoutManager al RecyclerView
        recyclerView.adapter = ScoreChallengeAdapter(ScoreChallengeProvider.scoreChallengeList)
    }

}