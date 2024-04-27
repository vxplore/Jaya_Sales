package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import androidx.compose.runtime.mutableStateOf
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import com.example.jayasales.Routes
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagerHomeViewModel @Inject constructor(
    private val repo: Repository
):WirelessViewModel(){

    private val opendialog = mutableStateOf(false)
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
            MyDataIds.managerLogout->{
                opendialog.value = !opendialog.value
            }
            MyDataIds.managerDismiss->{
                opendialog.value = !opendialog.value
            }
            MyDataIds.managerConfirm -> {
                opendialog.value = !opendialog.value
                doLogOut()
            }
            MyDataIds.order->{
                navigation {
                    navigate(Routes.managerOrder.full)
                }
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }
    init {
        mapData(
            MyDataIds.managerOpendialog to opendialog
        )
    }

    private fun doLogOut() {
        repo.setIsLoggedIn(false)
        repo.removeUser()
        navigation {
            navigate(Routes.login.full) {
                popUpTo(Routes.managerHome.full)
            }
        }
    }
}