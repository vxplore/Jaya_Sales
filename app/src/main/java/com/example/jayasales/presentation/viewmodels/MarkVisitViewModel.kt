package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import com.example.jayasales.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class MarkVisitViewModel @Inject constructor(
):WirelessViewModel(){
    private val comments = mutableStateOf("")
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
            MyDataIds.back->{
                popBackStack()
            }
            MyDataIds.comments->{
                comments.value = arg as String
            }
            MyDataIds.visitShop->{
                navigation {
                    navigate(Routes.home.full)
                }
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }
    init {
        mapData(
            MyDataIds.comments to comments,
        )

        setStatusBarColor(Color(0xFFFFEB56), false)
        updateMapData(37.7749, -122.4194)
    }
    private fun updateMapData(latitude: Double, longitude: Double) {
        _latitude.value = latitude
        _longitude.value = longitude
    }
}