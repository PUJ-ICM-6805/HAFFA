package com.example.haffa.start

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.haffa.R
import com.example.haffa.databinding.FragmentShareRouteBinding
import com.example.haffa.model.Route
import com.example.haffa.service.ImageService
import com.example.haffa.service.RouteService
import org.osmdroid.util.GeoPoint
import java.io.File
import java.io.IOException
import java.text.DateFormat
import java.time.LocalDate
import java.util.Date
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException


class ShareRoute : Fragment() {
    private lateinit var binding: FragmentShareRouteBinding

    // Códigos de solicitud únicos
    private val REQUEST_CAMERA_PERMISSION = 100
    private val REQUEST_GALLERY_PERMISSION = 101
    private val REQUEST_IMAGE_CAPTURE = 102
    private val REQUEST_PICK_IMAGE = 103

    // Variables para almacenar los datos recibidos
    private var distanceTraveled: Float = 0f
    private var elapsedTimeSeconds: Long = 0
    private var minAltitudeMeters: Float = 0f
    private var maxAltitudeMeters: Float = 0f
    private var averageAccelerationMS2: Float = 0f
    private var currentDateTime: String = ""
    private var polylineData: List<GeoPoint>? = null
    private var localImageUri: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShareRouteBinding.inflate(inflater, container, false)
        val view = binding.root

        val buttonbShareRoute = binding.bShareRoute

        arguments?.let {
            elapsedTimeSeconds = it.getLong("elapsedTimeSeconds")
            minAltitudeMeters = it.getFloat("minAltitudeMeters")
            maxAltitudeMeters = it.getFloat("maxAltitudeMeters")
            averageAccelerationMS2 = it.getFloat("averageAccelerationMS2")
            currentDateTime = it.getString("currentDateTime", "")
            polylineData = it.getSerializable("polylineData") as? List<GeoPoint>
        }
        // Construir la cadena de texto con los datos
        val dataText = """
        Distancia: ${distanceTraveled}m
        Altura máxima: ${maxAltitudeMeters}m
        Altura mínima: ${minAltitudeMeters}m
        Aceleración promedio: ${averageAccelerationMS2}m/s²
        Calificación obtenida: 0
        Duración: ${elapsedTimeSeconds}s
        Fecha de la ruta: $currentDateTime""".trimIndent()

        // Configurar el texto en tvData
        binding.tvData.text = dataText

        // Llamada a calculateDistance si tienes polylineData
        polylineData?.let {
            if (it.size >= 2) {
                val startCoordinate = it[0]
                val endCoordinate = it[it.size - 1]
                calculateDistance(
                    startCoordinate.latitude,
                    startCoordinate.longitude,
                    endCoordinate.latitude,
                    endCoordinate.longitude
                )
            }
        }

        // Configurar un OnClickListener para el botón
        buttonbShareRoute.setOnClickListener {

            val routeName = binding.editText.text.toString()

            if (routeName.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please enter a route name.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else if (localImageUri == null) {
                Toast.makeText(
                    requireContext(),
                    "Please take a picture or select one from the gallery.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                Toast.makeText(
                    requireContext(),
                    "Route saved successfully.",
                    Toast.LENGTH_SHORT
                ).show()

                val routeName = binding.editText.text.toString()
                // Call calculateDistance if you have polylineData
                polylineData?.let {
                    if (it.size >= 2) {
                        val startCoordinate = it[0]
                        val endCoordinate = it[it.size - 1]
                        calculateDistance(
                            startCoordinate.latitude,
                            startCoordinate.longitude,
                            endCoordinate.latitude,
                            endCoordinate.longitude
                        )
                    }
                }

                val imageService = ImageService()
                imageService.upload(localImageUri!!) { downloadImageUri ->
                    saveRouteToDatabase(
                        routeName,
                        distanceTraveled,
                        maxAltitudeMeters,
                        minAltitudeMeters,
                        averageAccelerationMS2,
                        elapsedTimeSeconds,
                        polylineData,
                        downloadImageUri
                    )
                }

                // Create an instance of the destination fragment (StartRoute)
                val startRouteFragment = StartRoute()

                // Get the FragmentManager
                val fragmentManager = parentFragmentManager

                // Start the fragment transaction to replace StartRoute with FinishRoute
                fragmentManager.beginTransaction()
                    .replace(
                        R.id.frame_container,
                        startRouteFragment
                    )
                    .addToBackStack(null) // Add to the back stack
                    .commit()
            }
        }


        binding.buttomCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openCamera()
            } else {
                requestCameraPermission()
            }
        }

        binding.buttomGallery.setOnClickListener {
            openImageGallery()
        }

        return view
    }

    private fun calculateDistance(startLat: Double, startLon: Double, endLat: Double, endLon: Double) {
        if (startLat.isNaN() || startLon.isNaN() || endLat.isNaN() || endLon.isNaN()) {
            Log.e("CalculateDistance", "Invalid coordinates")
            return
        }

        val url = "https://graphhopper.com/api/1/route?" +
                "point=$startLat,$startLon&point=$endLat,$endLon&" +
                "vehicle=car&locale=es&key=07264c7c-0594-4c67-a4e8-e744c44fe306"  // Reemplaza con tu clave API

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val paths = response.getJSONArray("paths")
                    val path = paths.getJSONObject(0)
                    val distance = path.getDouble("distance")

                    // Actualizar la distancia
                    distanceTraveled = distance.toFloat()

                    // Actualizar la UI
                    updateDistanceUI()
                } catch (e: JSONException) {
                    Log.e("CalculateDistance", "Error parsing the JSON response", e)
                }
            },
            { error ->
                Log.e("CalculateDistance", "Error: ${error.message}")
            }
        )

        Volley.newRequestQueue(context).add(jsonObjectRequest)
    }



    private fun saveRouteToDatabase(
        routeName: String,
        distanceTraveled: Float,
        maxAltitudeMeters: Float,
        minAltitudeMeters: Float,
        averageAccelerationMS2: Float,
        elapsedTimeSeconds: Long,
        polylineData: List<GeoPoint>?, dowloadImageUri: Uri?
    ) {
        val locations: List<Map<String, Double>> = polylineData?.map {
            mapOf(
                "latitude" to it.latitude,
                "longitude" to it.longitude
            )
        } ?: listOf()

        var points =
            (distanceTraveled.toInt() / 10 + (maxAltitudeMeters - minAltitudeMeters).toInt() / 10 + elapsedTimeSeconds.toInt() / 10).toInt()
        val route = Route(
            routeName,
            maxAltitudeMeters,
            minAltitudeMeters,
            averageAccelerationMS2,
            points,
            distanceTraveled.toDouble(),
            elapsedTimeSeconds.toDouble(),
            LocalDate.now().toString(),
            dowloadImageUri.toString(),
            locations
        )

        val routeService = RouteService()
        routeService.save(route)
    }

    private fun openCamera() {
        context?.packageManager?.let {


            // Se crea el intent para tomar la foto
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // Se crea el archivo de la imagen
            var imageFile: File? = null
            try {
                imageFile = createImageFile()
            } catch (ex: IOException) {
                Log.w("Error:", ex)
            }
            if (imageFile != null) {
                // Se obtiene la URI de la imagen
                localImageUri =
                    FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.haffa.fileprovider",
                        imageFile
                    )
                // Se le asigna la URI al intent
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, localImageUri)
                try {
                    // Se lanza el intent
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                } catch (e: ActivityNotFoundException) {
                    Log.w("Camera app not found.", e)
                }
            }

        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = DateFormat.getDateInstance().format(Date())
        val imageFileName = "${timeStamp}.jpg"
        return File(
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            imageFileName
        )
    }

    private fun openImageGallery() {
        // Se crea el intent para abrir la galería
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        try {
            // Se lanza el intent
            photoGalleryActivityResultLauncher.launch(intent)
        } catch (e: ActivityNotFoundException) {
            Log.w("Gallery app not found.", e)
        }
    }

    // Se crea el ActivityResultLauncher para el intent de la galería
    private val photoGalleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            // Si el resultado es OK, se muestra la imagen en el ImageView
            val imageUri: Uri? = result.data?.data
            if (imageUri != null) {

                binding.imageView2.setImageURI(imageUri)
                localImageUri = imageUri
            }
        }
    }


    private fun requestCameraPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        explainPermissionRequirement(Manifest.permission.CAMERA, requestCode)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Camera permission is required to take pictures.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun explainPermissionRequirement(permission: String, requestCode: Int) {
        AlertDialog.Builder(requireContext())
            .setMessage("This permission is required for some functionality to work.")
            .setPositiveButton("Allow") { _, _ ->
                requestPermissions(arrayOf(permission), requestCode)
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun updateDistanceUI() {
        val points = (distanceTraveled.toInt() / 10 + (maxAltitudeMeters - minAltitudeMeters).toInt() / 10 + elapsedTimeSeconds.toInt() / 10).toInt()
        // Actualizar la interfaz de usuario con la nueva distancia
        activity?.runOnUiThread {
            binding.tvData.text = """
                Distancia: ${distanceTraveled}m
                Altura máxima: ${maxAltitudeMeters}m
                Altura mínima: ${minAltitudeMeters}m
                Aceleración promedio: ${averageAccelerationMS2}m/s²
                Calificación obtenida: $points
                Duración: ${elapsedTimeSeconds}s
                Fecha de la ruta: $currentDateTime
            """.trimIndent()
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    // Se muestra la imagen en el ImageView
                    binding.imageView2.setImageURI(localImageUri)
                }
            }
        }
    }

}