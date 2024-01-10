package com.example.jayasales.utility

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices

@SuppressLint("MissingPermission")
fun getLastLocation(context: Context, callback: (Double, Double, Throwable?) -> Unit){
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            callback(location.latitude,location.longitude,null)
        }
        .addOnFailureListener { exception ->
            callback(0.0,0.0,exception)
        }
}