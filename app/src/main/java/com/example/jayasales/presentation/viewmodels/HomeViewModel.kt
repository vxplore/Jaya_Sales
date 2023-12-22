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
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo : Repository
) : WirelessViewModel() {
    private val logoutAlert = mutableStateOf(false)
    private val opendialog = mutableStateOf(false)
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when (id) {
            MyDataIds.logout->{
                opendialog.value =! opendialog.value
            }
            MyDataIds.dismiss->{
                opendialog.value =! opendialog.value
            }
            MyDataIds.Confirm->{
                opendialog.value =! opendialog.value
               doLogOut()
            }
            MyDataIds.route->{
                navigation {
                    navigate(
                        Routes.selectRoute.full
                    )
                }
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }
    init {
        mapData(
            MyDataIds.opendialog to opendialog,
        )
        setStatusBarColor(Color(0xFFFFEB56), false)
    }
    private fun doLogOut() {
        repo.setIsLoggedIn(false)
        repo.removeUser()
        navigation {
            navigate(Routes.login.full){
                popUpTo(Routes.home.full)
            }
        }
    }

}