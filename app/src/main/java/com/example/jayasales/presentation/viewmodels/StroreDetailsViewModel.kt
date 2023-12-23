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
import com.example.jayasales.model.StoreDataResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
enum class TransactionTab{
    Sales,
    Payments
}

@HiltViewModel
class StoreDetailsViewModel @Inject constructor(
):WirelessViewModel(){
    private val selectedTransactionTab = mutableStateOf(TransactionTab.Sales)
    private val storeDetailsList = mutableStateListOf<StoreDataResponse>()
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
            MyDataIds.saleBtn->{
                selectedTransactionTab.value = TransactionTab.Sales
            }
            MyDataIds.paymentBtn->{
                selectedTransactionTab.value = TransactionTab.Payments
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {

    }

    init {
        mapData(
            MyDataIds.selectedTransactionTab to selectedTransactionTab,
            MyDataIds.storeDetailsList to storeDetailsList,
        )
        setStatusBarColor(Color(0xFFFFEB56), false)
        storeDetailsList.addAll(listOf(StoreDataResponse("32","Order Placed","Ram Krishna Store","September 10, 2023","12:40 Pm","43923","Paid","Cash")))
        storeDetailsList.addAll(listOf(StoreDataResponse("32","Order Placed","Ram Krishna Store","September 10, 2023","12:40 Pm","43923","₹43923 Due","Cash")))
        storeDetailsList.addAll(listOf(StoreDataResponse("32","Order Placed","Ram Krishna Store","September 10, 2023","12:40 Pm","43923","Paid","Cheque")))

    }
}