package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
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
class SalesMenViewModel @Inject constructor(
):WirelessViewModel(){
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
            MyDataIds.timeline->{
                navigation {
                    navigate(Routes.timeline.full)
                }
            }

            MyDataIds.openMap->{
                navigation {
                    navigate(Routes.managerMap.full)
                }
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData()
        setStatusBarColor(Color(0xFFFFEB56), true)
    }
}