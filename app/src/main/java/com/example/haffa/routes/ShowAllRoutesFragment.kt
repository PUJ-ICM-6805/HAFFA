package com.example.haffa.routes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haffa.databinding.FragmentShowAllRoutesBinding
import java.util.Date

class ShowAllRoutesFragment : Fragment() {

    private lateinit var binding: FragmentShowAllRoutesBinding
    private lateinit var adapter: RouteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShowAllRoutesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.cardView

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        adapter = RouteAdapter(requireContext(), sampleData())
        recyclerView.adapter = adapter
    }


    private fun sampleData(): MutableList<Route> {
        val journeys = mutableListOf<Route>()

        journeys.add(
            Route(
                distance = 50.5,
                name = "Monserrate",
                points = 100,
                duration = 2.5,
                altitude = 3000,
                imgUrl = "https://e00-expansion.uecdn.es/assets/multimedia/imagenes/2023/03/27/16799149846473.jpg",
                date = Date()
            )
        )

        journeys.add(
            Route(
                distance = 30.0,
                name = "La Conejera",
                points = 75,
                duration = 1.8,
                altitude = 3000,
                imgUrl = "https://e00-expansion.uecdn.es/assets/multimedia/imagenes/2023/03/27/16799149846473.jpg",
                date = Date()
            )
        )

        journeys.add(
            Route(
                distance = 70.2,
                name = "Patios",
                points = 150,
                duration = 3.2,
                altitude = 3000,
                imgUrl = "https://e00-expansion.uecdn.es/assets/multimedia/imagenes/2023/03/27/16799149846473.jpg",
                date = Date()
            )
        )

        return journeys
    }
}
