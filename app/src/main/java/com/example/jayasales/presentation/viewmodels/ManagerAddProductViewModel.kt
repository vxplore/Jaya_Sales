package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagerAddProductViewModel @Inject constructor(
):WirelessViewModel(){
    private val qty = mutableStateOf("")


    private val orderSearch = mutableStateOf("")
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
            MyDataIds.orderSearch->{
                orderSearch.value = arg as String
            }
            MyDataIds.qty -> {
                qty.value = arg as String

            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }
    init {
        mapData(
            MyDataIds.orderSearch to orderSearch,
            MyDataIds.qty to qty,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
    }
}