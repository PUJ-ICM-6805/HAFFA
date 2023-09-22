package com.example.haffa.routes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.haffa.databinding.FragmentShowSelectedRouteBinding

class ShowSelectedRouteFragment : Fragment() {

    private lateinit var binding: FragmentShowSelectedRouteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowSelectedRouteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val route = arguments?.getSerializable("route", Route::class.java)

        if (route != null) {
            showRouteData(route)
        }
    }

    private fun showRouteData(route: Route) {
        with(binding){
            title.text = route.name
            distance.text = route.distance.toString() + " km"
            duration.text = route.duration.toString() + " horas"
            altitude.text = route.altitude.toString() + " m"
            points.text = route.points.toString()
        }
        Glide.with(this).load(route.imgUrl).into(binding.imageView)
    }
}