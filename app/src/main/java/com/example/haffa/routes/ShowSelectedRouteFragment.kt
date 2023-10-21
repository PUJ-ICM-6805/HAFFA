package com.example.haffa.routes

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.haffa.R
import com.example.haffa.databinding.FragmentShowSelectedRouteBinding
import com.example.haffa.model.Route
import com.example.haffa.start.StartRoute

class ShowSelectedRouteFragment : Fragment() {

    private lateinit var binding: FragmentShowSelectedRouteBinding
    private var route: Route? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowSelectedRouteBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("route", route)
            val newFragment = MapsFragment()
            newFragment.arguments = bundle
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction()
                .replace(
                    R.id.frame_container,
                    newFragment
                )
                .addToBackStack(null) // Agregar a la pila de retroceso
                .commit()
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        route = arguments?.getSerializable("route", Route::class.java)

        route?.let { showRouteData(it) }
    }

    private fun showRouteData(route: Route) {
        with(binding){
            title.text = route.name
            distance.text = route.distance.toString() + " km"
            duration.text = route.duration.toString() + " horas"
            altitude.text = route.maxAltitude.toString() + " m"
            points.text = route.points.toString()
        }
        Glide.with(this).load(route.imgUrl).into(binding.imageView)
    }
}