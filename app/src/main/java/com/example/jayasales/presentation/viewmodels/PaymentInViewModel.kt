package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.SoftInputMode
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import com.example.jayasales.Routes
import com.example.jayasales.di.NoConnectivityException
import com.example.jayasales.model.PaymentIn
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class PaymentModeTab {
    Cash,
    Cheque,
    Online
}

@HiltViewModel
class PaymentInViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val comment = mutableStateOf("")
    private val paymentInList = mutableStateListOf<PaymentIn>()
    private val selectedPaymentMode = mutableStateOf(PaymentModeTab.Cash)
    private val instruction = mutableStateOf("")
    private val paymentInDialog = mutableStateOf(false)
    private val userId = mutableStateOf("")
    private val storeId = mutableStateOf("")
    private val orderId = mutableStateOf("")
    private val index = mutableStateOf(0)
    private var clickedButtonText = ""
    private val paymentLoadingState = mutableStateOf(false)
    private val lostInternet = mutableStateOf(false)
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

            MyDataIds.index -> {
                index.value = arg as Int
            }

            MyDataIds.comment -> {
                comment.value = arg as String
            }

            MyDataIds.cashbtn -> {
                selectedPaymentMode.value = PaymentModeTab.Cash
                clickedButtonText = arg as String
                Log.d("fvvfv", clickedButtonText)
            }

            MyDataIds.chequebtn -> {
                selectedPaymentMode.value = PaymentModeTab.Cheque
                clickedButtonText = arg as String
                Log.d("fvvfv", clickedButtonText)
            }

            MyDataIds.onlinebtn -> {
                selectedPaymentMode.value = PaymentModeTab.Online
                clickedButtonText = arg as String
                Log.d("fvvfv", clickedButtonText)
            }

            MyDataIds.instruction -> {
                instruction.value = arg as String
            }

            MyDataIds.savebtn -> {
                receivePayment()
                //paymentInDialog.value = !paymentInDialog.value
                navigation {
                }
            }

            MyDataIds.paymentDone -> {
                paymentInDialog.value = !paymentInDialog.value
                navigation {
                    navigate(Routes.parties.full)
                }
            }

            MyDataIds.tryagain -> {
                lostInternet.value = false
                paymentIn()
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.comment to comment,
            MyDataIds.paymentInList to paymentInList,
            MyDataIds.selectedPaymentMode to selectedPaymentMode,
            MyDataIds.instruction to instruction,
            MyDataIds.paymentInDialog to paymentInDialog,
            MyDataIds.paymentLoadingState to paymentLoadingState,
            MyDataIds.lostInternet to lostInternet,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        setSoftInputMode(SoftInputMode.adjustPan)
        paymentIn()
    }

    private suspend fun handleNoConnectivity() {
        withContext(Dispatchers.Main) {
            lostInternet.value = true
        }
    }

    private fun paymentIn() {
        paymentLoadingState.value = true
        viewModelScope.launch(Dispatchers.Main) {
            try {
                userId.value = repo.getUserId()!!
                storeId.value = repo.getUId()!!
                Log.d("dc", storeId.value)
                val response = repo.paymentIn(userId.value, storeId.value)
                if (response?.status == true) {
                    Log.d("fgvgvf", response.toString())
                    val list = response.data
                    withContext(Dispatchers.Main) {
                        paymentInList.clear()
                        paymentInList.addAll(list)
                        // repo.setOrderId(paymentInList.map { it.id })
                        val orderIds = response.data[index.value].id

                        repo.setOrderId(orderIds)
                        Log.d("OrderIdLog", orderIds)
                    }
                }
            } catch (e: Exception) {
                handleNoConnectivity()
                Log.e("gbggbb", "${e.message}")
            } finally {
                paymentLoadingState.value = false
            }
        }
    }

    private fun receivePayment() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                userId.value = repo.getUserId()!!
                storeId.value = repo.getUId()!!
                Log.d("vffvv", storeId.value)
                orderId.value = repo.getOrderId()!!
                Log.d("vffvv", orderId.value)
                val response = repo.receivePayment(
                    userId.value,
                    orderId.value,
                    storeId.value,
                    comment.value,
                    clickedButtonText,
                    instruction.value
                )
                Log.d("fdvf", clickedButtonText)
                Log.d("fdvf", userId.value)
                Log.d("fdvf", storeId.value)
                Log.d("fdvf", orderId.value)
                Log.d("fdvf", comment.value)
                Log.d("fdvf", clickedButtonText)
                if (response?.status == true) {
                    paymentInDialog.value = !paymentInDialog.value
                    toast(response.message)
                } else {
                    if (response != null) {
                        toast(response.message)
                    }

                }
            }catch (e:NoConnectivityException){
                handleNoConnectivity()
            }
        }
    }
}
