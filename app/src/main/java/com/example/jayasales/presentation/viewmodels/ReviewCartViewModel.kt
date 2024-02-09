package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.State
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
import com.example.jayasales.model.Cart
import com.example.jayasales.model.ReviewCartDataResponse
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReviewCartViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val reviewInstruction = mutableStateOf("")
    private val reviewCartDialog = mutableStateOf(false)
    private val reviewCart = mutableStateListOf<Cart>()
    private val reviewCartDetails = mutableStateListOf<ReviewCartDataResponse>()
    private val userId = mutableStateOf("")
    private val storeId = mutableStateOf("")
    private val cartId = mutableStateOf("")
    private val taxAmount = mutableStateOf("")
    private val cgst = mutableStateOf("")
    private val gst = mutableStateOf("")
    private val total = mutableStateOf("")
    private val totalQuantity = mutableStateOf("")
    private val reviewLoadingState = mutableStateOf(false)
    private val lostInternet = mutableStateOf(false)
    private val index = mutableStateOf(0)

    val taxAmountState: State<String> get() = taxAmount
    val cgstState: State<String> get() = cgst
    val gstState: State<String> get() = gst
    val totalState: State<String> get() = total
    val totalQuantityState: State<String> get() = totalQuantity
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
                placeOrder()
            }

            MyDataIds.orderPlaced -> {
                navigation {
                    navigate(Routes.parties.full)
                }
            }

            MyDataIds.remove -> {
                remove()
                index.value = arg as Int
                repo.setCartId(reviewCart[index.value].cart_id)
                reviewCart()
            }

            MyDataIds.tryagain -> {
                lostInternet.value = false
                reviewCart()
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
            MyDataIds.reviewCartDetails to reviewCartDetails,
            MyDataIds.taxAmountState to taxAmountState,
            MyDataIds.cgstState to cgstState,
            MyDataIds.gstState to gstState,
            MyDataIds.totalState to totalState,
            MyDataIds.totalQuantityState to totalQuantityState,
            MyDataIds.reviewLoadingState to reviewLoadingState,
            MyDataIds.lostInternet to lostInternet,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        setSoftInputMode(SoftInputMode.adjustPan)
        reviewCart()
    }

    private suspend fun handleNoConnectivity() {
        withContext(Dispatchers.Main) {
            lostInternet.value = true
        }
    }

    private fun reviewCart() {
        reviewLoadingState.value = true
        viewModelScope.launch {
            try {
                userId.value = repo.getUserId()!!
                storeId.value = repo.getUId()!!
                val response = repo.reviewCart(userId.value, storeId.value)
                if (response?.status == true) {
                    taxAmount.value = response.sub_total
                    cgst.value = response.cgst
                    gst.value = response.gst
                    total.value = response.total
                    totalQuantity.value = response.data.size.toString()
                    Log.d("hvfmvf", totalQuantity.value)
                    reviewCart.clear()
                    reviewCart.addAll(response.data)
                }
                remove()
            } catch (e: NoConnectivityException) {
                handleNoConnectivity()
                Log.e("hjvh", "${e.message}")
            } finally {
                reviewLoadingState.value = false
            }
        }
    }

    private fun remove() {
        reviewLoadingState.value = true
        viewModelScope.launch {
            try {
                userId.value = repo.getUserId()!!
                storeId.value = repo.getUId()!!
                cartId.value = repo.getCartId()!!
                val response = repo.remove(userId.value, storeId.value, cartId.value)
                if (response?.status == true) {
                    val removedProductId = reviewCart[index.value].cart_id
                    reviewCart.removeAll { it.cart_id == removedProductId }
                    toast(response.message)
                    reviewCart()
                }
            } catch (e: NoConnectivityException) {
                handleNoConnectivity()
            } finally {
                reviewLoadingState.value = false
            }
        }
    }


    private fun placeOrder() {
        viewModelScope.launch {
            userId.value = repo.getUserId()!!
            storeId.value = repo.getUId()!!
            val response = repo.placeOrder(userId.value, storeId.value)
            if (response?.status == true) {
                reviewCartDialog.value = !reviewCartDialog.value
            } else {
                if (response != null) {
                    toast(response.message)
                }
            }
        }
    }
}