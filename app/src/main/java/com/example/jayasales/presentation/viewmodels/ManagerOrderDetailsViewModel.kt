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
import com.example.jayasales.model.DistributorDatum
import com.example.jayasales.model.DistributorDetailsProduct
import com.example.jayasales.model.DistributorOrderDetailsDataResponse
import com.example.jayasales.model.Order
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagerOrderDetailsViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val profilename = mutableStateOf("")
    private val orderDetails = mutableStateListOf<DistributorDetailsProduct>()
    private val orderDetailsTab = mutableStateListOf<Order>()
    private val userId = mutableStateOf("")
    private val orderId = mutableStateOf("")
    private val loadingState = mutableStateOf(false)
    private val orders = mutableStateOf("")
    private val status = mutableStateOf("")
    private val item = mutableStateOf("")
    private val name = mutableStateOf("")
    private val date = mutableStateOf("")
    private val location = mutableStateOf("")
    private val state = mutableStateOf("")
    private val pin = mutableStateOf("")
    private val productId = mutableStateOf("")

    val orderState: State<String> get() = orders
    val statusState: State<String> get() = status
    val itemState: State<String> get() = item
    val namesState: State<String> get() = name
    val dateState: State<String> get() = date
    val locationState: State<String> get() = location
    val stateState: State<String> get() = state
    val pinState: State<String> get() = pin
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when (id) {
            MyDataIds.profilename -> {
                profilename.value = arg as String
            }

            MyDataIds.back -> {
                popBackStack()
            }

            MyDataIds.addProduct -> {
                navigation {
                    navigate(Routes.managerAddProduct.full)
                }
            }

            MyDataIds.edit -> {
                val (clickedRouteIndex, clickedProductId) = arg as Pair<Int, String>
                val clickedRoute = orderDetails.getOrNull(clickedRouteIndex)
                if (clickedRoute != null) {
                    repo.setUpdateProductId(clickedProductId)
                    Log.d("ClickedProductId", clickedProductId)

                } else {
                    Log.e(
                        "ProductIds",
                        "No route found for clicked route index: $clickedRouteIndex"
                    )
                }
            }

            MyDataIds.delete -> {
                val (clickedRouteIndex, clickedProductId) = arg as Pair<Int, String>
                val clickedRoute = orderDetails.getOrNull(clickedRouteIndex)
                if (clickedRoute != null) {
                    repo.setUpdateProductId(clickedProductId)
                    Log.d("ClickedProductId", clickedProductId)
                    deleteProduct()
                } else {
                    Log.e(
                        "ProductIds",
                        "No route found for clicked route index: $clickedRouteIndex"
                    )
                }
            }

            MyDataIds.update -> {
                updateProduct()
            }

            MyDataIds.confirmOrder -> {
                confirmOrder()
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.profilename to profilename,
            MyDataIds.orderDetails to orderDetails,
            MyDataIds.loadingState to loadingState,
            MyDataIds.orderState to orderState,
            MyDataIds.statusState to statusState,
            MyDataIds.itemState to itemState,
            MyDataIds.namesState to namesState,
            MyDataIds.dateState to dateState,
            MyDataIds.locationState to locationState,
            MyDataIds.stateState to stateState,
            MyDataIds.pinState to pinState,
        )

        oredrDtailis()
        setStatusBarColor(Color(0xFFFFEB56), true)
    }

    private fun oredrDtailis() {
        loadingState.value = true
        userId.value = repo.getUserId()!!
        orderId.value = repo.getDistributorOrderId()!!
        viewModelScope.launch {
            try {
                val response = repo.distributorOrderDetails(userId.value, orderId.value)
                if (response?.status == true) {
                    orderDetails.clear()
                    orderDetails.addAll(response.products)
                    val order = response.order.firstOrNull()
                    order?.let {
                        orders.value = it.order_id
                        status.value = it.order_status
                        item.value = it.items.toString()
                        name.value = it.distributor.name
                        date.value = it.created_at
                        location.value = it.location.name
                        state.value = it.location.state
                        pin.value = it.location.pincode
                    }
                }
            } catch (e: Exception) {
                Log.e("ygdch", e.message.toString())
            } finally {
                loadingState.value = false
            }
        }
    }

    private fun updateProduct() {
        loadingState.value = true
        userId.value = repo.getUserId()!!
        orderId.value = repo.getDistributorOrderId()!!
        productId.value = repo.getUpdateProductId()!!
        viewModelScope.launch {
            try {
                val response = repo.updateQuantity(
                    userId.value,
                    orderId.value,
                    productId.value,
                    profilename.value
                )
                if (response?.status == true) {
                    oredrDtailis()
                    toast(response.message)
                }
            } catch (e: Exception) {
                Log.e("ygdch", e.message.toString())
            } finally {
                loadingState.value = false
            }
        }
    }

    private fun deleteProduct() {
        loadingState.value = true
        userId.value = repo.getUserId()!!
        orderId.value = repo.getDistributorOrderId()!!
        productId.value = repo.getUpdateProductId()!!
        viewModelScope.launch {
            try {
                val response = repo.deleteProduct(userId.value, orderId.value, productId.value)
                if (response?.status == true) {
                    oredrDtailis()
                    toast(response.message)
                }
            } catch (e: Exception) {
                Log.e("ygdch", e.message.toString())
            } finally {
                loadingState.value = false
            }
        }
    }

    private fun confirmOrder() {
        loadingState.value = true
        userId.value = repo.getUserId()!!
        orderId.value = repo.getDistributorOrderId()!!
        viewModelScope.launch {
            try {
                val response = repo.confirmOrder(userId.value,orderId.value)
                if (response?.status == true){
                    toast(response.message)
                    navigation {
                        navigate(Routes.managerOrder.full)
                    }
                }else{
                    if (response != null) {
                        toast(response.message)
                    }
                }
            }catch (e: Exception) {
                Log.e("ygdch", e.message.toString())
            } finally {
                loadingState.value = false
            }
        }
    }
}