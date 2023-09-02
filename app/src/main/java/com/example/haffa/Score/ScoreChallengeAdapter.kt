package com.example.myapplicationviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.haffa.R

class ScoreChallengeAdapter(val scoreChallengeList: List<ScoreChallengeData>) :
    RecyclerView.Adapter<ScoreChallengeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreChallengeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ScoreChallengeViewHolder(layoutInflater.inflate(R.layout.score_challenge_card, parent, false))
    }

    override fun getItemCount(): Int {
        return scoreChallengeList.size
    }

    override fun onBindViewHolder(holder: ScoreChallengeViewHolder, position: Int) {
        val item = scoreChallengeList[position]
        holder.render(item)
    }
}
