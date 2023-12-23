package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MarkVisitViewModel @Inject constructor(
):WirelessViewModel(){
    private val _latitude = mutableStateOf(0.0)
    val latitude: State<Double> get() = _latitude

    private val _longitude = mutableStateOf(0.0)
    val longitude: State<Double> get() = _longitude
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when(id){

        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }
    init {
        mapData()
        updateMapData(37.7749, -122.4194)
    }
    private fun updateMapData(latitude: Double, longitude: Double) {
        _latitude.value = latitude
        _longitude.value = longitude
    }
}