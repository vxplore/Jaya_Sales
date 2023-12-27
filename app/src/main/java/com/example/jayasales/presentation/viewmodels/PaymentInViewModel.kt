package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.SoftInputMode
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import com.example.jayasales.Routes
import com.example.jayasales.model.PaymentInList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

enum class PaymentModeTab {
    Cash,
    Cheque,
    Online
}

@HiltViewModel
class PaymentInViewModel @Inject constructor(
) : WirelessViewModel() {
    private val comment = mutableStateOf("")
    private val paymentInList = mutableStateListOf<PaymentInList>()
    private val selectedPaymentMode = mutableStateOf(PaymentModeTab.Cash)
    private val instruction = mutableStateOf("")
    private val paymentInDialog = mutableStateOf(false)
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
        setStatusBarColor(Color(0xFFFFEB56), false)
        setSoftInputMode(SoftInputMode.adjustPan)

        paymentInList.addAll(
            listOf(
                PaymentInList(
                    "22",
                    "43343",
                    "Sep 10, 2023",
                    "43923.00 Due",
                    "12:40 Pm",
                    "10%"
                )
            )
        )
        paymentInList.addAll(
            listOf(
                PaymentInList(
                    "23",
                    "43343",
                    "Sep 10, 2023",
                    "43923.00 Due",
                    "12:40 Pm",
                    "20%"
                )
            )
        )
        paymentInList.addAll(
            listOf(
                PaymentInList(
                    "24",
                    "43343",
                    "Sep 10, 2023",
                    "43923.00 Due",
                    "12:40 Pm",
                    "00%"
                )
            )
        )
        paymentInList.addAll(
            listOf(
                PaymentInList(
                    "25",
                    "43343",
                    "Sep 10, 2023",
                    "43923.00 Due",
                    "12:40 Pm",
                    "20%"
                )
            )
        )
    }
}