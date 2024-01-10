package com.example.jayasales.presentation.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.AppContext
import com.example.jayasales.MyDataIds
import com.example.jayasales.Routes
import com.example.jayasales.repository.Repository
import com.example.jayasales.utility.PermissionHandler
import com.example.jayasales.utility.updateLocation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarkVisitViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val permissionHandler = PermissionHandler()
    private val comments = mutableStateOf("")
    private val storeId = mutableStateOf("")
    private val userId = mutableStateOf("")
    private val currentLat = mutableStateOf("")
    private val currentLong = mutableStateOf("")
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when (id) {
            MyDataIds.back -> {
                popBackStack()
            }

            MyDataIds.comments -> {
                comments.value = arg as String
            }

            MyDataIds.visitShop -> {
                navigation {
                    navigate(Routes.home.full)
                }

                markVisit()
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.comments to comments,
            MyDataIds.currentLat to currentLat,
            MyDataIds.currentLong to currentLong,
        )
        tryGetLocation()
        setStatusBarColor(Color(0xFFFFEB56), true)
    }

    private fun markVisit() {
        viewModelScope.launch {
            storeId.value = repo.getUId()!!
            Log.d("fvbdf", "$storeId")
            userId.value = "USER_78u88isit6yhadolutedd"
            try {
                val response = repo.markVisit(
                    storeId.value,
                    userId.value,
                    currentLat.value,
                    currentLong.value,
                    comments.value
                )
                Log.d("jhyhjy", "${currentLat.value}")
                Log.d("jhyhjy", "${currentLong.value}")
                Log.d("jhyhjy", "${comments.value}")
                if (response?.status == true) {
                    Log.d("hgbj", response.toString())
                    toast(response!!.message)
                } else {
                    Log.d("hgbj", response.toString())
                    toast("Something went wrong")
                }
            } catch (e: Exception) {
                Log.e("hgbj", "Error: ${e.message}")
                toast("Check network connection")
            }
        }
    }
    @OptIn(ExperimentalPermissionsApi::class)
    private fun tryGetLocation() {

        viewModelScope.launch {
            val granted = permissionHandler.check(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
            )
            if (granted?.allPermissionsGranted == true) {
                getLocation()
            } else {
                val result = permissionHandler.request(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                )
                if (result?.all { it.value } == true) {
                    getLocation()
                } else {
                }
            }
        }
    }
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(AppContext.app)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                toast("${location?.latitude},${location?.longitude}")
                Log.d("MyL", "latitude ${location?.latitude}, longitude ${location?.longitude}")
                if (location != null) {
                    currentLat.value = location?.latitude.toString()!!
                    currentLong.value = location?.longitude.toString()!!
                    Log.d("bgbgg","${currentLat.value}")
                    //onBtnClick()
                }else{
                    //onBtnClick()
                    //toast("Please enable Location from settings")
                }
            }
    }

}