package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import com.example.jayasales.Routes
import com.example.jayasales.di.NoConnectivityException
import com.example.jayasales.model.DashBoardData
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val opendialog = mutableStateOf(false)
    private val nameState = mutableStateOf("")
    private val emailState = mutableStateOf("")
    private val routeState = mutableStateOf("")
    private val userId  = mutableStateOf("")
    private val dashboardPaymentList = mutableStateListOf<DashBoardData>()
    private val payments = mutableStateOf("")
    private val sells = mutableStateOf("")
    private val lostInternet = mutableStateOf(false)
    private val storeDtlsLoadingState = mutableStateOf(false)
    val paymentState: State<String> get() = payments
    val sellState: State<String> get() = sells
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when (id) {
            MyDataIds.logout -> {
                opendialog.value = !opendialog.value
            }

            MyDataIds.returnRequest -> {
                navigation {
                    navigate(Routes.returnRequest.full)
                }
            }

            MyDataIds.dismiss -> {
                opendialog.value = !opendialog.value
            }

            MyDataIds.Confirm -> {
                opendialog.value = !opendialog.value
                doLogOut()
            }

            MyDataIds.route -> {
                navigation {
                    navigate(
                        Routes.selectRoute.full
                    )
                }
            }

            MyDataIds.parties -> {
                navigation {
                    navigate(Routes.parties.full)
                }
            }

            MyDataIds.transactions -> {
                navigation {
                    navigate(Routes.paymentIn.full)
                }
            }

            MyDataIds.items -> {
                navigation {
                    navigate(Routes.items.full)
                }
            }

            MyDataIds.markAttendance -> {
                navigation {
                    navigate(Routes.markAttendance.full)
                }
            }

            MyDataIds.notification -> {
                navigation {
                    navigate(Routes.notification.full)
                }
            }
        }
    }


    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.opendialog to opendialog,
            MyDataIds.nameState to nameState,
            MyDataIds.emailState to emailState,
            MyDataIds.routeState to routeState,
            MyDataIds.paymentState to paymentState,
            MyDataIds.sellState to sellState,
            MyDataIds.lostInternet to lostInternet,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        dashboard()

        viewModelScope.launch {
            Log.d("hnvfn", "${repo.getLogUId()}")
            nameState.value = repo.getLogUId()!!
            emailState.value = repo.getLogEmail()!!
            routeState.value = repo.getRouteName()!!
        }
    }

    private fun doLogOut() {
        repo.setIsLoggedIn(false)
        repo.removeUser()
        navigation {
            navigate(Routes.login.full) {
                popUpTo(Routes.home.full)
            }
        }
    }
    private fun dashboard(){
        storeDtlsLoadingState.value = true
        viewModelScope.launch {
            userId.value = repo.getUserId()!!
            try {
                val response = repo.dashboard(userId.value)
                if (response?.status == true){
                    val list = response.data
                    mainScope {
                        dashboardPaymentList.clear()
                        dashboardPaymentList.addAll(listOf(list))
                        payments.value = list.payments
                        sells.value = list.sells.toString()
                    }
                }
            } catch (e: NoConnectivityException) {
                handleNoConnectivity()
                Log.e("fvfr", "${e.message}")
            }
            finally {
                storeDtlsLoadingState.value = false
            }
        }
    }
    private suspend fun handleNoConnectivity() {
        withContext(Dispatchers.Main) {
            lostInternet.value = true
        }
    }
}