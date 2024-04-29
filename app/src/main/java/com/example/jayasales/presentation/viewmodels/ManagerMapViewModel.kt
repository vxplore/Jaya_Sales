package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import androidx.compose.runtime.mutableStateOf
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagerMapViewModel @Inject constructor(
):WirelessViewModel(){
    private val grantPermission = mutableStateOf(false)
    private val latitude = mutableStateOf(22.6497)
    private val longitude = mutableStateOf(88.3386)
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
    }
}