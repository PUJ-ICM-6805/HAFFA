package com.example.myapplicationviews

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.haffa.R

class ScoreChallengeViewHolder  (itemView: View) : RecyclerView.ViewHolder(itemView){

    val Score = itemView.findViewById<TextView>(R.id.tvPersonalScore)
    val Description = itemView.findViewById<TextView>(R.id.tvDescriptionScore)

    fun render (scoreChallengeModel: ScoreChallengeData){
        Score.text = scoreChallengeModel.score
        Description.text = scoreChallengeModel.descriptionScore
    }
}