package com.example.haffa

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

class PermissionManager(private val activity: AppCompatActivity) {

    // Callback para cuando se otorga un permiso
    private var permissionGrantedCallback: (() -> Unit)? = null

    // Función para solicitar un permiso
    private val getPermission = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Se llama al callback si el permiso fue otorgado
            permissionGrantedCallback?.invoke()
        } else {
            // Se muestra un snackbar si el permiso fue denegado
            showPermissionDeniedSnackbar()
        }
        permissionGrantedCallback = null
    }

    // Función para solicitar un permiso
    fun requestPermission(
        permission: String,
        rationale: String,
        permissionGrantedCallback: (() -> Unit)? = null
    ) {
        this.permissionGrantedCallback = permissionGrantedCallback

        // Se verifica si el permiso ya fue otorgado
        when {
            // Si el permiso ya fue otorgado, se llama al callback
            ContextCompat.checkSelfPermission(
                activity,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                permissionGrantedCallback?.invoke()
            }

            // Si el permiso fue denegado anteriormente, se muestra un snackbar
            activity.shouldShowRequestPermissionRationale(permission) -> {
                showPermissionRationaleSnackbar(rationale, permission)
            }

            // Si el permiso no fue otorgado, se solicita
            else -> {
                getPermission.launch(permission)
            }
        }
    }

    // Función para mostrar un snackbar con el mensaje de que el permiso fue denegado
    private fun showPermissionRationaleSnackbar(rationale: String, permission: String) {
        val snackbar = Snackbar.make(
            activity.findViewById(android.R.id.content),
            rationale, Snackbar.LENGTH_LONG
        )
            .setAction("Retry") {
                // Request permission again
                getPermission.launch(permission)
                permissionGrantedCallback = null
            }
        snackbar.show()
    }

    // Función para mostrar un snackbar con el mensaje de que el permiso fue denegado
    private fun showPermissionDeniedSnackbar() {
        val snackbar = Snackbar.make(
            activity.findViewById(android.R.id.content),
            "Permission denied. Please go to app settings to enable the permission.",
            Snackbar.LENGTH_LONG
        )
            .setAction("Open Settings") {
                openAppSettings()
            }
        snackbar.show()
    }

    // Función para abrir la configuración de la aplicación
    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }
}
