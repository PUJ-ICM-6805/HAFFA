package com.example.myapplicationviews

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.haffa.R

class ScorePointViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    val Score = itemView.findViewById<TextView>(R.id.tvPersonalScore)
    val tvDateScore = itemView.findViewById<TextView>(R.id.tvDateScore)

    fun render (scorePointsModel: ScorePointData){
        Score.text = scorePointsModel.score.toString()
        tvDateScore.text = scorePointsModel.date
    }
}