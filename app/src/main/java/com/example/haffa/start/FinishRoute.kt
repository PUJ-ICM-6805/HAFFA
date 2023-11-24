package com.example.haffa.start

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.icu.text.SimpleDateFormat
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haffa.R
import com.example.haffa.databinding.FragmentFinishRouteBinding
import com.example.haffa.notifications.NotificationAssets
import com.example.haffa.utils.PermissionManager
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import org.osmdroid.util.GeoPoint
import java.util.Locale
import kotlin.math.sqrt
import java.util.Date


class FinishRoute : Fragment() {
    private lateinit var binding: FragmentFinishRouteBinding

    private var startTime: Long = 0
    private var distanceTraveled: Float = 0f
    private var lastLocation: Location? = null
    private var currentAltitude: Float = 0f
    private var minAltitude: Float = Float.MAX_VALUE
    private var maxAltitude: Float = Float.MIN_VALUE
    private var averageAcceleration: Float = 0f
    private var accelerationReadings: MutableList<Float> = mutableListOf()
    private var routePoints: MutableList<GeoPoint> = mutableListOf()
    private var routeOverlay: Polyline? = null

    // Variables para almacenar los datos que se registran
    private var elapsedTimeSeconds: Long = 0
    private var minAltitudeMeters: Float = 0f
    private var maxAltitudeMeters: Float = 0f
    private var averageAccelerationMS2: Float = 0f
    private var currentDateTime: String = ""

    // Variable para almacenar la polilínea
    private var polylineData: MutableList<GeoPoint> = mutableListOf()

    private lateinit var sensorManager: SensorManager
    private var pressureSensor: Sensor? = null
    private var accelerometerSensor: Sensor? = null
    private lateinit var myLocationOverlay: MyLocationNewOverlay

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            if (lastLocation != null) {
                distanceTraveled += lastLocation!!.distanceTo(location)
                if (distanceTraveled>20){
                    Log.d("Pruebas", "Distancia recorrida: $distanceTraveled metros")
                    notificationAssets.showNotification(12,"Adiós",1,"sÍ",requireContext())
                }

            }
            lastLocation = location
            val newPoint = GeoPoint(location.latitude, location.longitude)
            routePoints.add(newPoint)
            drawPolyline()
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) {}
    }

    private val pressureListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                if (it.sensor.type == Sensor.TYPE_PRESSURE) {
                    val pressure = it.values[0]
                    currentAltitude = SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, pressure)

                    if (currentAltitude < minAltitude) {
                        minAltitude = currentAltitude
                    }
                    if (currentAltitude > maxAltitude) {
                        maxAltitude = currentAltitude
                    }
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    private val accelerometerListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    val acceleration = sqrt(it.values[0] * it.values[0] + it.values[1] * it.values[1] + it.values[2] * it.values[2])
                    accelerationReadings.add(acceleration)
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    private lateinit var notificationAssets: NotificationAssets

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        notificationAssets = NotificationAssets(requireContext())
        binding = FragmentFinishRouteBinding.inflate(inflater, container, false)
        val view = binding.root

        val ctx = requireContext()
        Configuration.getInstance().load(ctx, ctx.getSharedPreferences("osm_prefs", Context.MODE_PRIVATE))

        binding.map.setTileSource(TileSourceFactory.MAPNIK)
        binding.map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
        binding.map.setMultiTouchControls(true)
        binding.map.controller.setZoom(18.0)

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            setupLocationOverlay()
            startTracking()
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }


        binding.bFinishRoute.setOnClickListener {
            stopTracking()
            elapsedTimeSeconds = (SystemClock.elapsedRealtime() - startTime) / 1000
            minAltitudeMeters = minAltitude
            maxAltitudeMeters = maxAltitude

            if (accelerationReadings.isNotEmpty()) {
                averageAccelerationMS2 = accelerationReadings.sum() / accelerationReadings.size
            }

            // Obtener la fecha y hora actuales y formatearlas como un String
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            currentDateTime = dateFormat.format(Date())

            // Log para depuración


            // Almacenar la polilínea
            polylineData = routePoints

            // Logs para depuración
            Log.d("FinishRoute", "Distancia recorrida: $distanceTraveled metros")
            Log.d("FinishRoute", "Tiempo transcurrido: $elapsedTimeSeconds segundos")
            Log.d("FinishRoute", "Altitud mínima: $minAltitudeMeters metros")
            Log.d("FinishRoute", "Altitud máxima: $maxAltitudeMeters metros")
            Log.d("FinishRoute", "Aceleración promedio: $averageAccelerationMS2 m/s^2")
            Log.d("FinishRoute", "Fecha y hora actuales: $currentDateTime")

            // Log para la polilínea
            Log.d("FinishRoute", "Polilínea: $polylineData")

            // Crear un Bundle para pasar los datos
            val bundle = Bundle()
            bundle.putFloat("distanceTraveled", distanceTraveled)
            bundle.putLong("elapsedTimeSeconds", elapsedTimeSeconds)
            bundle.putFloat("minAltitudeMeters", minAltitudeMeters)
            bundle.putFloat("maxAltitudeMeters", maxAltitudeMeters)
            bundle.putFloat("averageAccelerationMS2", averageAccelerationMS2)
            bundle.putString("currentDateTime", currentDateTime)
            bundle.putSerializable("polylineData", ArrayList(polylineData))

            // Crear una instancia de ShareRoute y establecer los argumentos
            val shareRouteFragment = ShareRoute()
            shareRouteFragment.arguments = bundle

            // Usar el FragmentManager para reemplazar el Fragmento actual con ShareRoute
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, shareRouteFragment) // usa el ID de tu contenedor de Fragmentos
                .addToBackStack(null)
                .commit()
        }


        return view
    }

    private fun setupLocationOverlay() {
        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), binding.map)
        myLocationOverlay.enableMyLocation()
        myLocationOverlay.enableFollowLocation()
        binding.map.overlays.add(myLocationOverlay)
    }

    private fun startTracking() {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Verificar si el permiso está disponible
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val newPoint = GeoPoint(lastLocation!!.latitude, lastLocation!!.longitude)
            routePoints.add(newPoint)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10f, locationListener)
            startTime = SystemClock.elapsedRealtime()
            lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        } else {
            // Si no se concede el permiso, puedes optar por mostrar un mensaje al usuario o tomar otra acción adecuada
            // Por ejemplo, podrías solicitar nuevamente el permiso
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

    private fun stopTracking() {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.removeUpdates(locationListener)
    }

    private fun drawPolyline() {
        if (routeOverlay == null) {
            routeOverlay = Polyline() // Crear la polilínea
            routeOverlay?.color = Color.BLUE // Establecer el color de la polilínea
            binding.map.overlays.add(routeOverlay) // Agregar la polilínea al mapa
        }
        routeOverlay?.setPoints(routePoints) // Establecer los puntos de la polilínea
        binding.map.invalidate() // Refrescar el mapa
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 1) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                setupLocationOverlay()
                startTracking()
            } else {
                // Manejar el caso en que el usuario no concede los permisos
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
        pressureSensor?.also { pressure ->
            sensorManager.registerListener(pressureListener, pressure, SensorManager.SENSOR_DELAY_NORMAL)
        }
        accelerometerSensor?.also { accelerometer ->
            sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
        sensorManager.unregisterListener(pressureListener)
        sensorManager.unregisterListener(accelerometerListener)
    }
}
