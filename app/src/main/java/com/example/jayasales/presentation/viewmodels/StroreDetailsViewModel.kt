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
import com.example.jayasales.model.Store
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    private val storeId  = mutableStateOf("")
    private val userId  = mutableStateOf("")
    private val storeName = mutableStateOf("")
    private val due = mutableStateOf("")
    private val address = mutableStateOf("")
    private val phoneNumber = mutableStateOf("")
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
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        storeDtls()
       /* storeDetailsList.addAll(
            listOf(
                StoreDataResponse(
                    "32",
                    "Order Placed",
                    "Ram Krishna Store",
                    "September 10, 2023",
                    "12:40 Pm",
                    "43923",
                    "Paid",
                    "Cash"
                )
            )
        )
        storeDetailsList.addAll(
            listOf(
                StoreDataResponse(
                    "32",
                    "Order Placed",
                    "Ram Krishna Store",
                    "September 10, 2023",
                    "12:40 Pm",
                    "43923",
                    "₹43923 Due",
                    "Cash"
                )
            )
        )
        storeDetailsList.addAll(
            listOf(
                StoreDataResponse(
                    "32",
                    "Order Placed",
                    "Ram Krishna Store",
                    "September 10, 2023",
                    "12:40 Pm",
                    "43923",
                    "Paid",
                    "Cheque"
                )
            )
        )*/

    }
    private fun storeDtls(){
        viewModelScope.launch {
            storeId.value = repo.getUId()!!
            Log.d("fvbdf","$storeId")
            userId.value = "USER_78u88isit6yhadolutedd"
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
            }catch (e: Exception) {
                Log.e("ghb", "${e.message}")
            }
        }
    }
}