package com.example.jayasales.presentation.viewmodels

import android.annotation.SuppressLint
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarkVisitViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
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
                viewModelScope.launch {
                    markVisit()
                    navigation {
                        navigate(Routes.home.full)
                    }
                }
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

    /*private suspend fun markVisit() {
        try {
            val api = ApiInterface.ApiClient.apiService
            val response = api.markVisit(
                repo.getUId() ?: "",
                "USER_78u88isit6yhadolutedd",
                currentLat.value ?: "",
                currentLong.value ?: "",
                comments.value ?: ""
            )

            Log.d("jhyhjy", currentLat.value)
            Log.d("jhyhjy", currentLong.value)
            Log.d("jhyhjy", comments.value)
            Log.d("jhyhjy", response.toString())

            if (response.isSuccessful) {
                Log.d("hgbj", response.toString())
                toast(response.message())
            } else {
                Log.d("hgbj", response.toString())
                toast("${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("hgbj", "Error: ${e.message}")
            toast("Check network connection")
        }
    }*/


    private fun markVisit() {
        viewModelScope.launch(Dispatchers.IO) {
            storeId.value = repo.getUId()!!
            Log.d("fvbdf", "$storeId")
            userId.value = repo.getUserId()!!
            val response = repo.markVisit(
                userId.value,
                storeId.value,
                currentLat.value,
                currentLong.value,
                comments.value
            )
            Log.d("jhyhjy", currentLat.value)
            Log.d("jhyhjy", currentLong.value)
            Log.d("jhyhjy", comments.value)
            Log.d("jhyhjy", storeId.value)
            Log.d("jhyhjy", userId.value)
            if (response?.status == true) {
                Log.d("hgbj", response.toString())
                toast(response.message)
            } else {
                Log.d("hgbj", response.toString())
                if (response != null) {
                    toast(response.message)
                }
            }
        }
    }

    private fun tryGetLocation() {

        viewModelScope.launch {
            val p = listOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
            )

            val granted = p.checkPermission()
            if (granted?.allPermissionsGranted == true) {
                getLocation()
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
                    Log.d("bgbgg", currentLat.value)
                    //onBtnClick()
                } else {
                    //onBtnClick()
                    //toast("Please enable Location from settings")
                }
            }
    }

}