package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
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
class MarkAttendanceViewModel @Inject constructor(
):WirelessViewModel(){
    private val attencomments = mutableStateOf("")
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
           MyDataIds.attencomments->{
               attencomments.value = arg as String
           }
           MyDataIds.timeSheet->{
               navigation {
                   navigate(Routes.timeSheet.full)
               }
           }
       }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }
    init {
        mapData(
            MyDataIds.attencomments to attencomments,
        )
        setStatusBarColor(Color(0xFFFFEB56),false)
    }
}