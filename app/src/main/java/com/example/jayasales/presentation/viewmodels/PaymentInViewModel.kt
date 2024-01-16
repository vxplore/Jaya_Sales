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
    private var clickedButtonText = ""
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
            MyDataIds.cashbtn, MyDataIds.chequebtn, MyDataIds.onlinebtn -> {
                clickedButtonText = arg as String

            }

            MyDataIds.comment -> {
                comment.value = arg as String
            }

            MyDataIds.cashbtn -> {
                selectedPaymentMode.value = PaymentModeTab.Cash
            }

            MyDataIds.chequebtn -> {
                selectedPaymentMode.value = PaymentModeTab.Cheque
            }

            MyDataIds.onlinebtn -> {
                selectedPaymentMode.value = PaymentModeTab.Online
            }

            MyDataIds.instruction -> {
                instruction.value = arg as String
            }

            MyDataIds.savebtn -> {
                receivePayment()
                paymentInDialog.value = !paymentInDialog.value
                navigation {
                }
            }

            MyDataIds.paymentDone -> {
                paymentInDialog.value = !paymentInDialog.value
                navigation {
                    navigate(Routes.parties.full)
                }
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
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        setSoftInputMode(SoftInputMode.adjustPan)
        paymentIn()
    }

    private fun paymentIn() {
        viewModelScope.launch(Dispatchers.Main) {
            userId.value = "USER_78u88isit6yhadolutedd"
            storeId.value = repo.getUId()!!
            val response = repo.paymentIn(userId.value,storeId.value )
            if (response?.status == true) {
                Log.d("fgvgvf", response.toString())
                val list = response.data
                withContext(Dispatchers.Main) {
                    paymentInList.clear()
                    paymentInList.addAll(list)
                    repo.setOrderId(paymentInList.map { it.id })
                }
            }
        }
    }
    private fun receivePayment(){
        viewModelScope.launch(Dispatchers.Main) {
            userId.value = "USER_78u88isit6yhadolutedd"
            storeId.value = repo.getUId()!!
            orderId.value = repo.getOrderId()!!
            val response = repo.receivePayment(userId.value,storeId.value,orderId.value,comment.value,instruction.value,)
            if (response?.status == true) {
                toast(response.message)
            }
        }
    }
}
