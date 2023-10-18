package com.example.haffa

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import io.reactivex.Observable
import kotlinx.coroutines.suspendCancellableCoroutine

class MyLocationService {

    // Función para obtener la última ubicación del usuario
    @SuppressLint("MissingPermission")
    suspend fun getUserLastLocation(context: Context): Location? {

        // Se crea el cliente de ubicación
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // Se verifica que el GPS esté activado
        val isGPSEnabled =
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        // Si el GPS no está activado, se retorna null
        if (!isGPSEnabled) {
            return null
        }

        // Se obtiene la última ubicación del usuario
        return suspendCancellableCoroutine { continuation ->
            fusedLocationProviderClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        continuation.resume(result) {}
                    } else {
                        continuation.resume(null) {}
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    continuation.resume(it) {}
                }
                addOnFailureListener {
                    continuation.resume(null) {}
                }
                addOnCanceledListener {
                    continuation.resume(null) {}
                }
            }
        }
    }

    // Función para obtener la ubicación en tiempo real del usuario
    @SuppressLint("MissingPermission")
    fun getRealTimeLocation(context: Context): Observable<Location> {
        // Se crea el request de ubicación
        val mLocationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .apply {
                setMinUpdateDistanceMeters(5F)
                setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                setWaitForAccurateLocation(true)
            }.build()

        // Se crea el cliente de ubicación
        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        // Se retorna un observable con la ubicación
        return Observable.create { emitter ->
            val mLocationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult
                    for (location in locationResult.locations) {
                        emitter.onNext(location)
                    }
                }
            }

            mFusedLocationClient.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                Looper.getMainLooper()
            )

            emitter.setCancellable {
                mFusedLocationClient.removeLocationUpdates(mLocationCallback)
            }
        }
    }
}