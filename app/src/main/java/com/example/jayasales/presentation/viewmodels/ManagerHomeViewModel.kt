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
import com.example.jayasales.model.DistributorDatum
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagerHomeViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val nameState = mutableStateOf("")
    private val emailState = mutableStateOf("")
    private val loadingState = mutableStateOf(false)
    private val opendialog = mutableStateOf(false)
    private val userId = mutableStateOf("")
    private val orderCount = mutableStateOf("")
    private val salesmanCount = mutableStateOf("")
    private val distributorOrder = mutableStateListOf<DistributorDatum>()
    private val indexRouteId = mutableStateOf(0)
    val orderCountState: State<String> get() = orderCount
    val salesmanCountState: State<String> get() = salesmanCount
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when (id) {
            MyDataIds.back -> {
                popBackStack()
            }

            MyDataIds.managerLogout -> {
                opendialog.value = !opendialog.value
            }

            MyDataIds.managerDismiss -> {
                opendialog.value = !opendialog.value
            }

            MyDataIds.managerConfirm -> {
                opendialog.value = !opendialog.value
                doLogOut()
            }

            MyDataIds.order -> {
                navigation {
                    navigate(Routes.managerOrder.full)
                }
            }

            MyDataIds.confirmOrder -> {
                indexRouteId.value = arg as Int
                val orderId = distributorOrder[indexRouteId.value].order_id
                repo.setDistributorOrderId(orderId)
                Log.d("dyhdcvd",orderId)
                navigation {
                    navigate(Routes.managerOrderDetails.full)
                }
            }
            MyDataIds.salesMen -> {
                navigation {
                    navigate(Routes.salesMen.full)
                }
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.managerOpendialog to opendialog,
            MyDataIds.nameState to nameState,
            MyDataIds.emailState to emailState,
            MyDataIds.loadingState to loadingState,
            MyDataIds.orderCount to orderCountState,
            MyDataIds.salesmanCount to salesmanCountState,
            MyDataIds.distributorOrderList to distributorOrder,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        distributorOrder()

        viewModelScope.launch {
            Log.d("hnvfn", "${repo.getLogUId()}")
            nameState.value = repo.getLogUId()!!
            emailState.value = repo.getLogEmail()!!
        }
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

    private fun distributorOrder(){
        loadingState.value = true
        userId.value = repo.getUserId()!!
        Log.d("uhdk",userId.value)
        viewModelScope.launch {
            try {
                val response = repo.distributorOrder(userId.value)
                if (response?.status == true) {
                    distributorOrder.clear()
                    distributorOrder.addAll(response.data)
                    orderCount.value = response.order
                    salesmanCount.value = response.sales_man
                }
            }catch (e:Exception){
                Log.e("ygdch",e.message.toString())
            }finally {
                loadingState.value = false
            }
        }
    }
}