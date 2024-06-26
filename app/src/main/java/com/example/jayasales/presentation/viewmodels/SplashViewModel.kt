package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.BuildConfig
import com.example.jayasales.MyDataIds
import com.example.jayasales.Routes
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val versionName = mutableStateOf("")
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.versionName to versionName,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        versionName.value = BuildConfig.VERSION_NAME
        viewModelScope.launch {
            delay(3000)
            goToProperLogin()
        }
    }

    private fun goToProperLogin() {
        if (repo.getIsLoggedIn() ) {
            if (repo.getUserType()=="sales_man") {
                navigation {
                    navigate(Routes.home.full) {
                        popUpTo(Routes.splash.full)
                    }
                }
            }
            else{
                navigation {
                    navigate(Routes.managerHome.full) {
                        popUpTo(Routes.splash.full)
                    }
                }
            }
        } else {
            navigation {
                navigate(Routes.login.full) {
                    popUpTo(Routes.splash.full)
                }
            }
        }
    }
}