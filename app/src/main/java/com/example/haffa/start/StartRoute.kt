package com.example.haffa.start

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haffa.R
import com.example.haffa.databinding.FragmentStartRouteBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class StartRoute : Fragment() {

    // Variables para los elementos de la UI, el overlay de ubicación y el gestor de ubicación
    private lateinit var binding: FragmentStartRouteBinding
    private lateinit var myLocationOverlay: MyLocationNewOverlay
    private lateinit var locationManager: LocationManager

    // Listener que se activa cuando cambia la ubicación
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            // Cuando la ubicación cambia, actualiza la posición en el mapa
            val geoPoint = org.osmdroid.util.GeoPoint(location.latitude, location.longitude)
            binding.map.controller.animateTo(geoPoint)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Configuración inicial
        val ctx = requireContext()
        Configuration.getInstance().load(ctx, ctx.getSharedPreferences("osm_prefs", Context.MODE_PRIVATE))

        // Inflar la vista del fragmento
        binding = FragmentStartRouteBinding.inflate(inflater, container, false)
        val view = binding.root

        // Configuración del mapa
        binding.map.setTileSource(TileSourceFactory.MAPNIK)
        binding.map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
        binding.map.setMultiTouchControls(true)

        // Obtener el servicio de ubicación
        locationManager = ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Verificar si tenemos permiso de ubicación
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Si hay permiso, configurar el overlay de ubicación
            setupLocationOverlay()
        } else {
            // Si no hay permiso, solicitarlo
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

        // Listener del botón para iniciar ruta
        binding.bStartRoute.setOnClickListener {
            // Cambiar al fragmento de finalización de ruta
            val finishRouteFragment = FinishRoute()
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, finishRouteFragment)
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    // Método para configurar el overlay de ubicación
    private fun setupLocationOverlay() {
        // Crear y habilitar el overlay de ubicación
        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), binding.map)
        myLocationOverlay.enableMyLocation()
        myLocationOverlay.enableFollowLocation()
        binding.map.overlays.add(myLocationOverlay)
        binding.map.controller.setZoom(18.0) // Establecer el nivel de zoom aquí

        // Si hay permiso, iniciar las actualizaciones de ubicación
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
        }
    }

    // Ciclo de vida del fragmento
    override fun onResume() {
        super.onResume()
        binding.map.onResume() // Reanudar el mapa
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause() // Pausar el mapa
    }

    override fun onDestroy() {
        super.onDestroy()
        // Detener las actualizaciones de ubicación cuando el fragmento se destruye
        locationManager.removeUpdates(locationListener)
    }
}
