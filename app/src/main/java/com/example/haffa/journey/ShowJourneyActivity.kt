package com.example.haffa.journey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.haffa.databinding.ActivityShowJourneyBinding

class ShowJourneyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowJourneyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowJourneyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val journey = intent.getSerializableExtra("journey", Journey::class.java)

        showJourneyData(journey as Journey)

    }

    fun showJourneyData (journey: Journey){
        with(binding){
            route.text = journey.route
            distance.text = journey.distance.toString() + " km"
            duration.text = journey.duration.toString() + " horas"
            altitude.text = journey.altitude.toString() + " m"
            points.text = journey.points.toString()
        }
        Glide.with(this).load(journey.imgUrl).into(binding.imageView)
    }
}