package com.example.haffa.journey.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haffa.databinding.ActivityShowAllJorneysBinding
import com.example.haffa.journey.adapter.JourneyAdapter
import com.example.haffa.journey.model.Journey
import java.util.Date


class ShowAllJourneysActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowAllJorneysBinding
    private lateinit var journeyAdapter: JourneyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowAllJorneysBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.cardView

        recyclerView.layoutManager = LinearLayoutManager(this)

        journeyAdapter = JourneyAdapter(this, sampleData())
        recyclerView.adapter = journeyAdapter
    }


    fun sampleData() : MutableList<Journey>{
        var journeys = mutableListOf<Journey>()

        journeys.add(
            Journey(
            distance = 50.5,
            route = "Route A",
            points = 100,
            duration = 2.5,
            imgUrl = "https://e00-expansion.uecdn.es/assets/multimedia/imagenes/2023/03/27/16799149846473.jpg",
            date = Date()
        )
        )

        journeys.add(
            Journey(
                distance = 30.0,
                route = "Route B",
                points = 75,
                duration = 1.8,
                imgUrl = "https://e00-expansion.uecdn.es/assets/multimedia/imagenes/2023/03/27/16799149846473.jpg",
                date = Date()
            )
        )

        journeys.add(
            Journey(
                distance = 70.2,
                route = "Route C",
                points = 150,
                duration = 3.2,
                imgUrl = "https://e00-expansion.uecdn.es/assets/multimedia/imagenes/2023/03/27/16799149846473.jpg",
                date = Date()
            )
        )

        return journeys
    }
}