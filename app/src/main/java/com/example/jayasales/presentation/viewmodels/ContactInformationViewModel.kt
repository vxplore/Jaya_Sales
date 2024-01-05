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
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactInformationViewModel @Inject constructor(
) : WirelessViewModel() {
    private val name = mutableStateOf("")
    private val phnNo = mutableStateOf("")
    private val email = mutableStateOf("")
    private val gst = mutableStateOf("")
    private val storeDialog = mutableStateOf(false)
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

            MyDataIds.c_name -> {
                name.value = arg as String
            }

            MyDataIds.phnNo -> {
                phnNo.value = arg as String
            }

            MyDataIds.email -> {
                email.value = arg as String
            }

            MyDataIds.gst -> {
                gst.value = arg as String
            }

            MyDataIds.addNow -> {
                storeDialog.value = !storeDialog.value
            }

            MyDataIds.backNow -> {
                navigation {
                    navigate(Routes.addNewStore.full)
                }
            }

            MyDataIds.addNewStore -> {
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
            MyDataIds.c_name to name,
            MyDataIds.phnNo to phnNo,
            MyDataIds.email to email,
            MyDataIds.gst to gst,
            MyDataIds.storeDialog to storeDialog,
        )

        setStatusBarColor(Color(0xFFFFEB56), true)
    }
}