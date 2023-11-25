package com.example.haffa.points

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haffa.databinding.FragmentPointsBinding
import com.example.haffa.model.Route
import com.example.haffa.service.RouteService


class Points : Fragment() {

    private lateinit var binding: FragmentPointsBinding
    private lateinit var adapter: PointsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPointsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadPointsData()
    }

    private fun setupRecyclerView() {
        binding.totalPointsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = PointsAdapter(requireContext(), emptyList())
        binding.totalPointsRecyclerView.adapter = adapter
    }

    private fun loadPointsData() {
        val routeService = RouteService()
        routeService.getAll { routes ->
            // Calcula la suma total de los puntos
            val totalPoints = routes.sumOf { it.points }

            // Actualiza el TextView en el hilo principal
            activity?.runOnUiThread {
                binding.totalPoints.text = totalPoints.toString()
            }

            // Actualiza el adaptador con los datos de las rutas
            adapter.updateData(routes)
        }
    }

}
