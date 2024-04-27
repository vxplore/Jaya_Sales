package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import androidx.compose.runtime.mutableStateListOf
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import com.example.jayasales.presentation.screens.DistributorOrder
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
        distributorOrder.addAll(listOf(DistributorOrder("1234","12","Noushad","12/04/24","Pending","PadmaBabu Road")))
        distributorOrder.addAll(listOf(DistributorOrder("1235","4","Sayan","1/03/24","Completed","Arambagh Basantapur")))
        distributorOrder.addAll(listOf(DistributorOrder("1236","7","Ricky","09/01/24","Pending","Kolkata")))
        distributorOrder.addAll(listOf(DistributorOrder("1237","9","Rakibuddin","18/04/24","Failed","Ripon Street")))
        distributorOrder.addAll(listOf(DistributorOrder("1238","1","Moloy","18/04/24","Ongoing","Bally, Howrah")))
    }
}