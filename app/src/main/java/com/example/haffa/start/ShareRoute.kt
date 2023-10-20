package com.example.haffa.start

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haffa.R
import com.example.haffa.databinding.FragmentShareRouteBinding
import org.osmdroid.util.GeoPoint

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShareRouteBinding.inflate(inflater, container, false)
        val view = binding.root

        // Obtener una referencia al botón bStartRoute usando View Binding
        val buttonbShareRoute = binding.bShareRoute

        // Recuperar datos del Bundle
        arguments?.let {
            distanceTraveled = it.getFloat("distanceTraveled")
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
        Velocidad promedio: ${averageAccelerationMS2}m/s²
        Calificación obtenida: 0
        Duración: ${elapsedTimeSeconds}s
        Fecha de la ruta: $currentDateTime""".trimIndent()

        // Configurar el texto en tvData
        binding.tvData.text = dataText

        // Configurar un OnClickListener para el botón
        buttonbShareRoute.setOnClickListener {
            // Crear una instancia del fragmento de destino (FinishRoute)
            val startRouteFragment = StartRoute()

            // Obtener el FragmentManager
            val fragmentManager = parentFragmentManager

            // Iniciar la transacción de fragmento para reemplazar StartRoute con FinishRoute
            fragmentManager.beginTransaction()
                .replace(
                    R.id.frame_container,
                    startRouteFragment
                )
                .addToBackStack(null) // Agregar a la pila de retroceso
                .commit()
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
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openGallery()
            } else {
                requestGalleryPermission()
            }
        }

        return view
    }

    private fun openCamera() {
        context?.packageManager?.let {
            // Verifica si el dispositivo tiene cámara
            if (it.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } else {
                Toast.makeText(
                    requireContext(),
                    "No camera available.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun openGallery() {
        val pickImageIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*" // Solo permitir la selección de imágenes
        }

        startActivityForResult(pickImageIntent, REQUEST_PICK_IMAGE)
    }


    private fun requestCameraPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION
        )
    }

    private fun requestGalleryPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_GALLERY_PERMISSION
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

            REQUEST_GALLERY_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        explainPermissionRequirement(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            requestCode
                        )
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Gallery access is required to choose pictures.",
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as? Bitmap
                    binding.imageView2.setImageBitmap(imageBitmap)
                }

                REQUEST_PICK_IMAGE -> {
                    val imageUri = data?.data
                    binding.imageView2.setImageURI(imageUri)
                }
            }
        } else {
            Toast.makeText(
                requireContext(),
                "No se ha seleccionado ninguna imagen.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
