package com.example.haffa.routes

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.haffa.R
import com.example.haffa.model.Route
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CustomCap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.RoundCap

class MapsFragment : Fragment() {

    private lateinit var route: Route

    private val callback = OnMapReadyCallback { googleMap ->
        // Extract locations from the Route object and draw polylines
        val circleCap = CustomCap(bitmapDescriptorFromVector(requireContext(), R.drawable.circle), 20f)
        val polylineOptions = PolylineOptions()
            .color(
                ContextCompat.getColor(requireContext(), R.color.violet))
            .width(20f)
            .startCap(circleCap)
            .endCap(circleCap)
        route.locations.forEach {
            val latLng = LatLng(it["latitude"]!!, it["longitude"]!!)
            polylineOptions.add(latLng)
        }
        googleMap.addPolyline(polylineOptions)

        val startLocation = route.locations.first()
        val startLatLng = LatLng(startLocation["latitude"]!!, startLocation["longitude"]!!)


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLatLng, 15f))
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            route = it.getSerializable("route", Route::class.java) as Route
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    companion object {
        fun newInstance(route: Route) = MapsFragment().apply {
            arguments = Bundle().apply {
                putSerializable("route", route)
            }
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

}
