package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import android.util.Log
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
import com.example.jayasales.model.DistributorDetailsProduct
import com.example.jayasales.model.SearchData
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagerAddProductViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val qty = mutableStateOf("")
    private val searchList = mutableStateListOf<SearchData>()
    private val loadingState = mutableStateOf(false)
    private val productId = mutableStateOf("")
    private val userId = mutableStateOf("")
    private val orderId = mutableStateOf("")


    private val orderSearch = mutableStateOf("")
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

            MyDataIds.orderSearch -> {
                orderSearch.value = arg as String
            }

            MyDataIds.qty -> {
                qty.value = arg as String

            }

            MyDataIds.search -> {
                searchList()
            }
            MyDataIds.addNow->{
                addProduct()
                navigation {
                    navigate(Routes.managerOrderDetails.full)
                }
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.orderSearch to orderSearch,
            MyDataIds.qty to qty,
            MyDataIds.searchList to searchList,
            MyDataIds.loadingState to loadingState,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
    }

    private fun searchList() {
        loadingState.value = true
        viewModelScope.launch {
            try {
                val response = repo.searchProduct(orderSearch.value)
                if (response?.status == true) {
                    searchList.clear()
                    searchList.addAll(listOf(response.data))
                    val pId = response.data.product_id
                    repo.setUpdateProductId(pId)
                } else {
                    if (response != null) {
                        toast(response.message)
                    }
                }
            } catch (e: Exception) {
                Log.e("ygdch", e.message.toString())
            } finally {
                loadingState.value = false
            }
        }
    }

    private fun addProduct() {
        loadingState.value = true
        userId.value = repo.getUserId()!!
        orderId.value = repo.getDistributorOrderId()!!
        productId.value = repo.getUpdateProductId()!!
        viewModelScope.launch {
            try {
                val response = repo.addProduct(userId.value,orderId.value,productId.value,qty.value)
                if (response?.status ==true){
                    toast(response.message)
            }
        }catch (e: Exception) {
                Log.e("ygdch", e.message.toString())
            } finally {
                loadingState.value = false
            }
    }
}
}