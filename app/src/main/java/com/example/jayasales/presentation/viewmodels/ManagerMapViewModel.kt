package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagerMapViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val grantPermission = mutableStateOf(false)
    private val latitude = mutableStateOf(0.0) // Changed to Double
    private val longitude = mutableStateOf(0.0) // Changed to Double

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
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.locationPermission to grantPermission,
            MyDataIds.mapLatitude to latitude,
            MyDataIds.mapLongitude to longitude,
        )

        viewModelScope.launch {
            try {
                repo.getlat()?.toDoubleOrNull()?.let {
                    latitude.value = it
                }
                repo.getlng()?.toDoubleOrNull()?.let {
                    longitude.value = it
                }
            } catch (e: Exception) {
                latitude.value = 0.0
                longitude.value = 0.0
            }
        }
    }
}
