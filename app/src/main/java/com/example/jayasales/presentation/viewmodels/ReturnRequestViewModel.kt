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
import com.example.jayasales.di.NoConnectivityException
import com.example.jayasales.model.AllBrandDataResponse
import com.example.jayasales.model.AllCategory
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReturnRequestViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val product = mutableStateOf("")
    private val brandEndPoint = mutableStateOf("sells/product_brands")
    private val brands = mutableStateListOf<AllBrandDataResponse.Brand>()
    private val categories = mutableStateListOf<AllCategory.Category>()
    private val categoryEndPoint = mutableStateOf("sells/product_categories")
    private val productName = mutableStateListOf<String>()
    private val lot = mutableStateOf("")
    private val reason = mutableStateListOf<String>()
    private val message = mutableStateOf("")
    private val storeNames = mutableStateListOf<String>()
    private val searchStoreName = mutableStateOf("")
    private val index = mutableStateOf("")
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

            MyDataIds.searchStoreName -> {
                searchStoreName.value = arg as String
            }
            MyDataIds.brandId->{
                index.value = arg as String
                Log.d("dvfv",index.value)
                repo.setReturnBrand(index.value)
            }
            MyDataIds.categoryId->{
                index.value = arg as String
                Log.d("dvfv",index.value)
                repo.setCategoryId(index.value)
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.product to product,
            MyDataIds.brandName to brands,
            MyDataIds.productCategory to categories,
            MyDataIds.lot to lot,
            MyDataIds.reason to reason,
            MyDataIds.message to message,
            MyDataIds.productName to productName,
            MyDataIds.storeNames to storeNames,
            MyDataIds.searchStoreName to searchStoreName,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        setSoftInputMode(SoftInputMode.adjustPan)
        fetchBrands()
        loadAllCategories()




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

    private fun fetchBrands() {
        val brandEndPoint = brandEndPoint.value
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.allBrands(brandEndPoint)
                withContext(Dispatchers.Main) {
                    brands.clear()
                    brands.add(AllBrandDataResponse.All)
                    if (response != null) {
                        brands.addAll(response.data)
                        /*for (brand in response.data) {
                            val uid = brand.uid
                            repo.setReturnBrand(uid)
                            Log.d("BrandUID", "Brand UID: $uid")
                        }*/
                    }
                }
            } catch (e: Exception) {
                // Handle exceptions
            }
        }
    }


    private fun loadAllCategories() {
        val categoryEndPoint = categoryEndPoint.value
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.allCategory(categoryEndPoint)
                if (response?.status == true) {
                    categories.clear()
                    categories.addAll(response.data)
                }
            } catch (e: NoConnectivityException) {

            }
        }
    }
}