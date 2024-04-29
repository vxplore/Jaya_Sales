package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import com.example.jayasales.presentation.screens.DistributorOrder
import com.example.jayasales.presentation.screens.TrackerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagerOrderViewModel @Inject constructor(
) :WirelessViewModel(){
    private val distributorOrder = mutableStateListOf<DistributorOrder>()
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
            MyDataIds.distributorOrder to distributorOrder,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)

        distributorOrder.addAll(listOf(DistributorOrder("1234","12","Noushad","12/04/24","Pending","PadmaBabu Road","Dispatched",
            listOf(TrackerItem("2/03/23","Booked"))
        )))

        distributorOrder.addAll(listOf(DistributorOrder("1234","12","Noushad","12/04/24","Pending","PadmaBabu Road","Received",
            listOf(TrackerItem("2/03/23","Booked"))
        )))
        distributorOrder.addAll(listOf(DistributorOrder("1234","12","Noushad","12/04/24","Pending","PadmaBabu Road","Booked",
            listOf(TrackerItem("2/03/23","Booked"))
        )))
        distributorOrder.addAll(listOf(DistributorOrder("1235","4","Sayan","1/03/24","Completed","Arambagh Basantapur", "Ready to Load",listOf(TrackerItem("2/04/23","Ready to Load")))))
    }
}