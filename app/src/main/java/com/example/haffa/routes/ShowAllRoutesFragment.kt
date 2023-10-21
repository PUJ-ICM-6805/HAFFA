package com.example.haffa.routes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haffa.databinding.FragmentShowAllRoutesBinding
import com.example.haffa.service.RouteService

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

        adapter = RouteAdapter(requireContext(), mutableListOf())
        recyclerView.adapter = adapter

        val routeService = RouteService()
        routeService.getAll { routes ->
            adapter.updateData(routes)
        }
    }

    override fun onResume() {
        super.onResume()
        val recyclerView = binding.cardView

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        adapter = RouteAdapter(requireContext(), mutableListOf())
        recyclerView.adapter = adapter

        val routeService = RouteService()
        routeService.getAll { routes ->
            adapter.updateData(routes)
        }
    }
}
