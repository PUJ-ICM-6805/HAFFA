package com.example.haffa.journey

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.haffa.databinding.JourneyCardLayoutBinding
import java.util.Locale

class JourneyAdapter(private val context: Context, private val journeys: List<Journey>) : RecyclerView.Adapter<JourneyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            JourneyCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentJourney = journeys[position]
        holder.bindData(currentJourney)

    }

    override fun getItemCount(): Int {
        return journeys.size
    }

    inner class ViewHolder(private val binding: JourneyCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat")
        fun bindData(currentJourney: Journey) {
            binding.title.text = currentJourney.route

            // Format the date to your desired format
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(currentJourney.date)
            binding.date.text = formattedDate

            Glide.with(context).load(currentJourney.imgUrl).into(binding.cardImg)

        }
    }
}