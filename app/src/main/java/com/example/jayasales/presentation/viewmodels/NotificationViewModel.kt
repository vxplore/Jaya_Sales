package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import com.example.jayasales.model.NotificationList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
):WirelessViewModel(){
    private val notificationList = mutableStateListOf<NotificationList>()
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
            MyDataIds.notificationList to notificationList,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)

        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))
        notificationList.addAll(listOf(NotificationList("Order","You got a new order","14:30 pm")))

    }
}