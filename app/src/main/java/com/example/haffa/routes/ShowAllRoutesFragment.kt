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
    private lateinit var routeService: RouteService
    private var phone: String? = null
    private var friendName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            phone = it.getString("PHONE")
            friendName = it.getString("FRIEND_NAME")
        }
        routeService = RouteService()
    }

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
        setupRecyclerView()

        if (phone != null) {
            // Handle the case when there is a specific phone number to filter by
            binding.title.text = "Routes for $friendName"
            loadRoutesForPhone(phone!!)
        } else {
            // Handle the default case where no specific phone number is specified
            loadAllRoutes()
        }
    }

    private fun setupRecyclerView() {
        binding.cardView.layoutManager = LinearLayoutManager(requireContext())
        adapter = RouteAdapter(requireContext(), mutableListOf())
        binding.cardView.adapter = adapter
    }

    private fun loadAllRoutes() {
        routeService.getAll { routes ->
            adapter.updateData(routes)
        }
    }

    private fun loadRoutesForPhone(phone: String) {
        routeService.getAllByPhone(phone) { routes ->
            adapter.updateData(routes)
        }
    }
}
