package com.example.travelsouvenir.utils

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.travelsouvenir.viewmodels.PlacesViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationCallback
import java.util.Locale
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.os.Looper

class LocationHelper(private val context: Context, private val viewModel: PlacesViewModel) {

    fun startLocationUpdates(
        fusedLocationClient: FusedLocationProviderClient,
        locationRequest: LocationRequest,
        locationState: MutableState<Location?>
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationState.value = locationResult.lastLocation
                    Log.d("Location", "Location updated: ${locationResult.lastLocation?.latitude}, ${locationResult.lastLocation?.longitude}")
                }
            },
            Looper.getMainLooper()
        )
    }

    fun getCityNameFromLocation(
        locationClient: FusedLocationProviderClient,
        imageUri: Uri
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("Location", "Location permission not granted.")
            return
        }

        locationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addressList: List<android.location.Address>?

                try {
                    addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (!addressList.isNullOrEmpty()) {
                        val cityName = addressList[0].locality ?: "Unknown"
                        Log.d("Location", "City Name: $cityName")
                        viewModel.loadPlaces(listOf(cityName))
                        PhotoHelper(context).createAlbumAndSavePhoto(cityName, imageUri)
                    } else {
                        Log.d("Location", "No address found")
                    }
                } catch (e: Exception) {
                    Log.e("Location", "Error retrieving address", e)
                }
            } else {
                Log.d("Location", "Location is null")
            }
        }
    }
}
