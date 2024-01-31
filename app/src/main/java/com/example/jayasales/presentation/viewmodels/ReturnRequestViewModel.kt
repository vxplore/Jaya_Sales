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
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReturnRequestViewModel @Inject constructor(
) : WirelessViewModel() {
    private val product = mutableStateOf("")
    private val brandName = mutableStateListOf<String>()
    private val productName = mutableStateListOf<String>()
    private val category = mutableStateListOf<String>()
    private val lot = mutableStateOf("")
    private val reason = mutableStateListOf<String>()
    private val message = mutableStateOf("")
    private val storeNames = mutableStateListOf<String>()
    private val searchStoreName = mutableStateOf("")
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

            MyDataIds.product -> {
                product.value = arg as String
            }

            MyDataIds.lot -> {
                lot.value = arg as String
            }

            MyDataIds.message -> {
                message.value = arg as String
            }
            MyDataIds.searchStoreName->{
                searchStoreName.value = arg as String
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.product to product,
            MyDataIds.brandName to brandName,
            MyDataIds.productCategory to category,
            MyDataIds.lot to lot,
            MyDataIds.reason to reason,
            MyDataIds.message to message,
            MyDataIds.productName to productName,
            MyDataIds.storeNames to storeNames,
            MyDataIds.searchStoreName to searchStoreName,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        setSoftInputMode(SoftInputMode.adjustPan)

        brandName.addAll(listOf("Jaya"))
        brandName.addAll(listOf("Mari"))
        brandName.addAll(listOf("Oreo"))
        brandName.addAll(listOf("Britannia"))

        category.addAll(listOf("Cream"))
        category.addAll(listOf("Namkin"))
        category.addAll(listOf("Sweet"))

        reason.addAll(listOf("R1"))
        reason.addAll(listOf("R2"))
        reason.addAll(listOf("R3"))
        reason.addAll(listOf("R4"))

        productName.addAll(listOf("P1"))
        productName.addAll(listOf("P2"))
        productName.addAll(listOf("P3"))
        productName.addAll(listOf("P4"))

        storeNames.addAll(listOf("Bikram Vandari"))
        storeNames.addAll(listOf("Bikrimart"))
        storeNames.addAll(listOf("Vxplore Technologies"))
        storeNames.addAll(listOf("Jaya"))
        storeNames.addAll(listOf("Burger King"))
    }
}