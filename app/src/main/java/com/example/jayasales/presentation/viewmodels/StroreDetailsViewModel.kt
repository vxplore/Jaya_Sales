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
import com.example.jayasales.model.OrderList
import com.example.jayasales.model.PaymentList
import com.example.jayasales.model.Store
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class TransactionTab {
    Sales,
    Payments
}

@HiltViewModel
class StoreDetailsViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val selectedTransactionTab = mutableStateOf(TransactionTab.Sales)
    private val storeDetailsList = mutableStateListOf<Store>()
    private val storeDetailsOrderList = mutableStateListOf<OrderList>()
    private val storeDetailsPaymentList = mutableStateListOf<PaymentList>()
    private val storeId  = mutableStateOf("")
    private val userId  = mutableStateOf("")
    private val storeName = mutableStateOf("")
    private val due = mutableStateOf("")
    private val address = mutableStateOf("")
    private val phoneNumber = mutableStateOf("")
    private val storeDtlsLoadingState = mutableStateOf(false)
    private val lostInternet = mutableStateOf(false)
    val storeNameState: State<String> get() = storeName
    val dueState: State<String> get() = due
    val addressState: State<String> get() = address
    val phoneNumberState: State<String> get() = phoneNumber
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

            MyDataIds.saleBtn -> {
                selectedTransactionTab.value = TransactionTab.Sales
            }

            MyDataIds.paymentBtn -> {
                selectedTransactionTab.value = TransactionTab.Payments
            }

            MyDataIds.paymentIn -> {
                navigation {
                    navigate(Routes.paymentIn.full)
                }
            }

            MyDataIds.newOrders -> {
                navigation {
                    navigate(Routes.newOrder.full)
                }
            }

            MyDataIds.markVisit -> {
                navigation {
                    navigate(Routes.markVisit.full)
                }
            }
            MyDataIds.tryagain->{
                lostInternet.value = false
                storeDtls()
                storeDtlsOrders()
                storeDtlsPayments()
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {

    }

    init {
        mapData(
            MyDataIds.selectedTransactionTab to selectedTransactionTab,
            MyDataIds.storeDetailsList to storeDetailsList,
            MyDataIds.storeNameState to storeNameState,
            MyDataIds.dueState to dueState,
            MyDataIds.addressState to addressState,
            MyDataIds.phoneNumberState to phoneNumberState,
            MyDataIds.storeDetailsOrderList to storeDetailsOrderList,
            MyDataIds.storeDetailsPaymentList to storeDetailsPaymentList,
            MyDataIds.storeDtlsLoadingState to storeDtlsLoadingState,
            MyDataIds.lostInternet to lostInternet
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        storeDtls()
        storeDtlsOrders()
        storeDtlsPayments()
    }

    private suspend fun handleNoConnectivity() {
        withContext(Dispatchers.Main) {
            lostInternet.value = true
        }
    }
    private fun storeDtls(){
        viewModelScope.launch {
            storeId.value = repo.getUId()!!
            Log.d("fvbdf","$storeId")
            userId.value = repo.getUserId()!!
            try {
                val response = repo.storeDetails(storeId.value,userId.value)
                if (response?.status == true) {
                    Log.d("ghb", "$response")
                    val list = response.store
                    mainScope {
                        storeDetailsList.clear()
                        storeDetailsList.addAll(listOf(list))
                        storeName.value = list.store_name
                        due.value = list.due_amount.toString()
                        address.value = list.address
                        phoneNumber.value = list.phone
                    }
                }
            }catch (e: NoConnectivityException) {
                handleNoConnectivity()
                Log.e("ghb", "${e.message}")
            }
        }
    }

    private fun storeDtlsOrders() {
        storeDtlsLoadingState.value = true
        viewModelScope.launch {
            storeId.value = repo.getUId()!!
            Log.d("fvbdf", "$storeId")
            userId.value = repo.getUserId()!!
            try {
                val response = repo.storeDetails(storeId.value, userId.value)
                if (response?.status == true) {
                    Log.d("fvfr", "$response")
                    val orderlist = response.order_list
                    if (orderlist.isNotEmpty()) {
                        Log.d("fvfr", "$orderlist")
                        mainScope {
                            storeDetailsOrderList.clear()
                            storeDetailsOrderList.addAll(orderlist)
                        }
                    } else {
                        Log.d("fvfr", "Order list is empty.")
                        mainScope {
                            storeDetailsOrderList.clear()
                        }
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

    private fun storeDtlsPayments() {
        storeDtlsLoadingState.value = true
        viewModelScope.launch {
            storeId.value = repo.getUId()!!
            Log.d("fvbdf", "$storeId")
            userId.value = repo.getUserId()!!
            try {
                val response = repo.storeDetails(storeId.value, userId.value)
                if (response?.status == true) {
                    Log.d("fvfr", "$response")
                    val orderlist = response.payment_list
                    if (orderlist.isNotEmpty()) {
                        Log.d("fvfr", "$orderlist")
                        mainScope {
                            storeDetailsPaymentList.clear()
                            storeDetailsPaymentList.addAll(orderlist)
                        }
                    } else {
                        Log.d("fvfr", "Order list is empty.")
                        mainScope {
                            storeDetailsOrderList.clear()
                        }
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
}