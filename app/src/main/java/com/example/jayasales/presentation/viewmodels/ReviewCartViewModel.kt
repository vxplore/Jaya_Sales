package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
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
import com.example.jayasales.model.ReviewCartDataResponse
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewCartViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val reviewInstruction = mutableStateOf("")
    private val reviewCartDialog = mutableStateOf(false)
    private val reviewCart = mutableStateListOf<ReviewCartDataResponse>()
    private val userId = mutableStateOf("")
    private val storeId = mutableStateOf("")
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

            MyDataIds.reviewInstruction -> {
                reviewInstruction.value = arg as String
            }

            MyDataIds.placeOrder -> {
                reviewCartDialog.value = !reviewCartDialog.value
            }

            MyDataIds.orderPlaced -> {
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
            MyDataIds.reviewInstruction to reviewInstruction,
            MyDataIds.reviewCartDialog to reviewCartDialog,
            MyDataIds.reviewCart to reviewCart,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        setSoftInputMode(SoftInputMode.adjustPan)
        reviewCart()
    }

    private fun reviewCart() {
        viewModelScope.launch {
            userId.value = "USER_78u88isit6yhadolutedd"
            storeId.value = repo.getUId()!!
            val response = repo.reviewCart(userId.value, storeId.value)
            if (response?.status == true) {
                reviewCart.clear()
                reviewCart.addAll(listOf(response))
            }
        }
    }
}