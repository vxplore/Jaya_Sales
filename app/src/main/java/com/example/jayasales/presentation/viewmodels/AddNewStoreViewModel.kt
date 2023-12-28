package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import androidx.compose.runtime.mutableStateListOf
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
class AddNewStoreViewModel @Inject constructor(
):WirelessViewModel(){
    private val storeName = mutableStateOf("")
    private val pin = mutableStateOf("")
    private val address = mutableStateOf("")
    private val city = mutableStateListOf<String>()
    private val state = mutableStateListOf<String>()
    private val addRoute = mutableStateListOf<String>()
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
            MyDataIds.storeName->{
                storeName.value = arg as String
            }
            MyDataIds.pin->{
                pin.value = arg as String
            }
            MyDataIds.address->{
                address.value = arg as String
            }
            MyDataIds.contactInformation->{
                navigation {
                    navigate(Routes.contactInformation.full)
                }
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }
    init {
        mapData(
            MyDataIds.storeName to storeName,
            MyDataIds.city to city,
            MyDataIds.state to state,
            MyDataIds.pin to pin,
            MyDataIds.address to address,
            MyDataIds.addRoute to addRoute
        )
        setStatusBarColor(Color(0xFFFFEB56), false)

        city.addAll(listOf("Arambagh"))
        city.addAll(listOf("Kolkata"))
        city.addAll(listOf("Bally"))
        city.addAll(listOf("Howrah"))

        state.addAll(listOf("A"))
        state.addAll(listOf("B"))
        state.addAll(listOf("C"))
        state.addAll(listOf("D"))

        addRoute.addAll(listOf("Harish Chandra Bose Lane"))
        addRoute.addAll(listOf("Harish Chandra Bose Lane"))
        addRoute.addAll(listOf("Harish Chandra Bose Lane"))
    }
}