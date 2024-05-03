package com.example.jayasales.presentation.viewmodels

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import com.example.jayasales.Routes
import com.example.jayasales.model.SalesManDatum
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesMenViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {

    private val salesmen = mutableStateListOf<SalesManDatum>()
    private val loadingState = mutableStateOf(false)
    private val userId = mutableStateOf("")
    private val indexRouteId = mutableStateOf(0)
    private val phoneNumber = mutableStateOf("")
    val phoneNumberState: State<String> get() = phoneNumber
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

            MyDataIds.timeline -> {
                indexRouteId.value = arg as Int
                val salesman = salesmen[indexRouteId.value].id
                val name = salesmen[indexRouteId.value].name
                repo.setSalesmenId(salesman)
                repo.setCallNo(name)
                Log.d("dyhdcvd",salesman)
                navigation {
                    navigate(Routes.timeline.full)
                }
            }

            MyDataIds.openMap -> {
                indexRouteId.value = arg as Int
                val lat = salesmen[indexRouteId.value].lat
                repo.setlat(lat)
                Log.d("dyhdcvd",lat)

                val lng = salesmen[indexRouteId.value].lng
                repo.setlng(lng)
                navigation {
                    navigate(Routes.managerMap.full)
                }
            }

           /* MyDataIds.call->{
                indexRouteId.value = arg as Int
                val phone = salesmen[indexRouteId.value].phone
                repo.setCallNo(phone)
                Log.d("dyhdcvd",phone)
            }*/

        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.salesmen to salesmen,
            MyDataIds.loadingState to loadingState,
            MyDataIds.phoneNumberState to phoneNumberState,
        )

        salesmen()
        setStatusBarColor(Color(0xFFFFEB56), true)
    }

    private fun salesmen() {
        loadingState.value = true
        userId.value = repo.getUserId()!!
        Log.d("uhdk", userId.value)
        viewModelScope.launch {
            try {
                val response = repo.salesMan(userId.value)
                if (response?.status == true) {
                    val list = response.data
                    salesmen.clear()
                    salesmen.addAll(list)

                    val index = indexRouteId.value
                    if (index in 0 until list.size) {
                        phoneNumber.value = list[index].phone
                        Log.d("hdxcdnfjd", phoneNumber.value)
                    } else {
                        Log.e("SalesMenViewModel", "Invalid index: $index")
                    }
                }
            } catch (e: Exception) {
                Log.e("ygdch", e.message.toString())
            } finally {
                loadingState.value = false
            }
        }
    }




}