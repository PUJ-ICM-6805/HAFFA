package com.example.myapplicationviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.haffa.R

class ScorePointAdapter(val scorePointsList: List<ScorePointData>) : RecyclerView.Adapter<ScorePointViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScorePointViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ScorePointViewHolder(layoutInflater.inflate(R.layout.score_points_card, parent, false))
    }

    override fun getItemCount(): Int {
         return scorePointsList.size
    }

    override fun onBindViewHolder(holder: ScorePointViewHolder, position: Int) {
        val item = scorePointsList[position]
        holder.render(item)
    }
}