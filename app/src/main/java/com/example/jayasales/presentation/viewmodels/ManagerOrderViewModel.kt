package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import android.util.Log
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
import com.example.jayasales.model.ConfirmOrderDatum
import com.example.jayasales.model.DistributorDatum
import com.example.jayasales.presentation.screens.DistributorOrder
import com.example.jayasales.presentation.screens.TrackerItem
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagerOrderViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val distributorOrder = mutableStateListOf<DistributorOrder>()
    private val distributorOrderList = mutableStateListOf<DistributorDatum>()
    private val indexRouteId = mutableStateOf(0)
    private val loadingState = mutableStateOf(false)
    private val opendialog = mutableStateOf(false)
    private val userId = mutableStateOf("")
    private val distributorConfirmOrder = mutableStateListOf<ConfirmOrderDatum>()
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

            MyDataIds.confirmOrder -> {
                indexRouteId.value = arg as Int
                val orderId = distributorOrderList[indexRouteId.value].order_id
                repo.setDistributorOrderId(orderId)
                Log.d("dyhdcvd", orderId)
                navigation {
                    navigate(Routes.managerOrderDetails.full)
                }
            }

        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.distributorOrderList to distributorOrderList,
            MyDataIds.loadingState to loadingState,
            MyDataIds.distributorOrder to distributorConfirmOrder,
        )

        confirmOderList()
        distributorOrder()
        setStatusBarColor(Color(0xFFFFEB56), true)

        distributorOrder.addAll(
            listOf(
                DistributorOrder(
                    "1234", "12", "Noushad", "12/04/24", "Pending", "PadmaBabu Road", "Dispatched",
                    listOf(TrackerItem("2/03/23", "Booked"))
                )
            )
        )

        distributorOrder.addAll(
            listOf(
                DistributorOrder(
                    "1234", "12", "Noushad", "12/04/24", "Pending", "PadmaBabu Road", "Received",
                    listOf(TrackerItem("2/03/23", "Booked"))
                )
            )
        )
        distributorOrder.addAll(
            listOf(
                DistributorOrder(
                    "1234", "12", "Noushad", "12/04/24", "Pending", "PadmaBabu Road", "Booked",
                    listOf(TrackerItem("2/03/23", "Booked"))
                )
            )
        )
        distributorOrder.addAll(
            listOf(
                DistributorOrder(
                    "1235",
                    "4",
                    "Sayan",
                    "1/03/24",
                    "Completed",
                    "Arambagh Basantapur",
                    "Ready to Load",
                    listOf(TrackerItem("2/04/23", "Ready to Load"))
                )
            )
        )
    }

    private fun distributorOrder() {
        loadingState.value = true
        userId.value = repo.getUserId()!!
        Log.d("uhdk", userId.value)
        viewModelScope.launch {
            try {
                val response = repo.distributorOrder(userId.value)
                if (response?.status == true) {
                    distributorOrderList.clear()
                    distributorOrderList.addAll(response.data)
                }
            } catch (e: Exception) {
                Log.e("ygdch", e.message.toString())
            } finally {
                loadingState.value = false
            }
        }
    }

    private fun confirmOderList() {
        loadingState.value = true
        userId.value = repo.getUserId()!!
        Log.d("uhdk", userId.value)
        viewModelScope.launch {
            try {
                val response = repo.distributorConfirmOrder(userId.value)
                if (response?.status == true){
                    distributorConfirmOrder.clear()
                    distributorConfirmOrder.addAll(response.data)
                }
            } catch (e: Exception) {
                Log.e("ygdch", e.message.toString())
            } finally {
                loadingState.value = false
            }
        }
    }
}