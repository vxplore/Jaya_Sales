package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.App
import com.example.jayasales.MyDataIds
import com.example.jayasales.Routes
import com.example.jayasales.di.NoConnectivityException
import com.example.jayasales.model.AllBrandDataResponse
import com.example.jayasales.model.AllCategory
import com.example.jayasales.model.AllProducts
import com.example.jayasales.model.Product
import com.example.jayasales.repository.ApiInterface
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class NewOrdersViewModel @Inject constructor(
    private val repository: Repository
) : WirelessViewModel() {
    private val brands = mutableStateListOf<AllBrandDataResponse.Brand>()
    private val categories = mutableStateListOf<AllCategory.Category>()
    private val selectedBrandTabId = mutableStateOf(0)
    private val selectedCategoryTabId = mutableStateOf("")
    private val searchProductQuery = mutableStateOf("")
    private val noOfItem = mutableStateOf(0)
    private val filterListOfProducts = mutableStateListOf<Product>()
    private val listOfProducts = mutableStateListOf<Product>()
    private val brandEndPoint = mutableStateOf("sells/product_brands")
    private val categoryEndPoint = mutableStateOf("sells/product_categories")
    private val brandId = mutableStateOf("")
    private val categoryId = mutableStateOf("")
    private val userId = mutableStateOf("")
    private val storeId = mutableStateOf("")
    private val quantity = mutableStateOf("")
    private val index = mutableStateOf(0)
    private val lostInternet = mutableStateOf(false)
    private val loadingState = mutableStateOf(false)
    private val emptyDataDialog = mutableStateOf(false)

    override fun eventBusDescription(): EventBusDescription? = null

    override fun interCom(message: InterCom) {}

    override fun onBack() {}

    override fun onNotification(id: Any?, arg: Any?) {
        when (id) {
            MyDataIds.back -> {
                navigation {
                    popBackStack()
                }
            }

            MyDataIds.orderIndex -> {
                index.value = arg as Int
            }

            MyDataIds.viewToCart -> {
                viewCart()

            }

            MyDataIds.brandChange -> {
                selectedBrandTabId.value = arg as Int
                Log.d("BrandChange", "Selected Brand ID: ${selectedBrandTabId.value}")
                onBrandChange()
                fetchProducts()
            }

            MyDataIds.productSearch -> {
                searchProductQuery.value = arg as String
                fetchProducts()
                filterProducts()
            }

            MyDataIds.categoryChange -> {
                selectedCategoryTabId.value = arg as String
                onCategoryChange()
                fetchProducts()
                filterProducts()
            }

            MyDataIds.tryagain -> {
                lostInternet.value = false
                fetchBrands()
                loadAllCategories()
                fetchProducts()
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {}

    private var job: Job? = null

    private fun searchLatch(ms: Long, block: () -> Unit) {
        val wasRunning = job != null
        job?.cancel()
        job = viewModelScope.launch {
            if (wasRunning) {
                delay(ms)
            }
            block()
        }
    }

    init {
        setup()
        fetchBrands()
        loadAllCategories()
        setStatusBarColor(Color(0xFFFFEB56), true)
    }

    private fun fetchBrands() {
        val brandEndPoint = brandEndPoint.value
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.allBrands(brandEndPoint)
                withContext(Dispatchers.Main) {
                    brands.clear()
                    brands.add(AllBrandDataResponse.All)
                    if (response != null) {
                        brands.addAll(response.data)
                        val selectedBrand = response.data.getOrNull(index.value)
                        if (selectedBrand != null) {
                            repository.setBrand(selectedBrand.uid)
                            Log.d("dcdsgcx", selectedBrand.uid)
                        }
                    }
                    fetchProducts()
                }
            } catch (e: NoConnectivityException) {
                handleNoConnectivity()
            }
        }
    }

    private fun loadAllCategories() {
        val categoryEndPoint = categoryEndPoint.value
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.allCategory(categoryEndPoint)
                withContext(Dispatchers.Main) {
                    categories.clear()
                    if (!categories.any { it.uid == AllCategory.All.uid }) {
                        categories.add(AllCategory.All)
                    }
                    if (response != null) {
                        categories.addAll(response.data)
                        val selectedCategoryId = selectedCategoryTabId.value
                        val matchingCategory = response.data.find { it.uid == selectedCategoryId }
                        val categoryId = matchingCategory?.uid ?: ""
                        repository.setCategory(categoryId)
                        Log.d("dcdscx", categoryId)
                    }
                    fetchProducts()
                    filterProducts()
                }
            } catch (e: NoConnectivityException) {
                handleNoConnectivity()
            }
        }
    }

    private suspend fun handleNoConnectivity() {
        withContext(Dispatchers.Main) {
            lostInternet.value = true
        }
    }

    private fun fetchProducts() {
        loadingState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val filterByBrandOrCategory =
                    brandId.value != AllBrandDataResponse.All.uid && brandId.value != "NoBrandSelected" ||
                            categoryId.value != AllCategory.All.uid
                val productbrandId: String
                val response = if (filterByBrandOrCategory) {
                    if (selectedBrandTabId.value != 0) {
                        productbrandId =
                            brands.getOrNull(selectedBrandTabId.value)?.uid ?: "NoBrandSelected"
                        val selectedBrandIndex = selectedBrandTabId.value
                        if (selectedBrandIndex in brands.indices) {
                            val selectedBrand = brands[selectedBrandIndex]
                            val productBrandId = selectedBrand.uid ?: "NoBrandSelected"
                            brandId.value = productBrandId
                        }
                    } else {
                        productbrandId = brands.getOrNull(selectedBrandTabId.value)?.uid ?: ""
                        brandId.value = ""
                    }
                    if (selectedCategoryTabId.value != AllCategory.All.uid) {
                        categoryId.value = selectedCategoryTabId.value
                    } else {
                        categoryId.value = ""
                    }
                    repository.allProducts(
                        categoryId.value,
                        brandId.value,
                        searchProductQuery.value
                    )
                } else {
                    repository.allProducts("", "", searchProductQuery.value)
                }

                withContext(Dispatchers.Main) {
                    if (response?.status == true) {
                        val list = response.data
                        listOfProducts.clear()
                        listOfProducts.addAll(list)
                        val productId = response.data.getOrNull(index.value)?.uid ?: ""
                        repository.setProductId(productId)
                        filterProducts()
                        selectDefaultBrand()
                        if (list.isEmpty()) {
                            Log.d("fetchProducts", "No data available")
                        }
                    }
                }
            } catch (e: NoConnectivityException) {
                handleNoConnectivity()
                Log.d("fgffg", "${e.message}")
            } finally {
                loadingState.value = false
            }
        }
    }

    private fun viewCart() {
        viewModelScope.launch(Dispatchers.IO) {
            userId.value = repository.getUserId() ?: ""
            storeId.value = repository.getUId() ?: ""

            val productsInCart = App.cart.get().filter { (_, quantity) ->
                quantity > 0
            }.map { (productId, quantity) ->
                AllProducts(productId, quantity.toString())
            }

            if (productsInCart.isNotEmpty()) {
                val viewCartRequest =
                    ApiInterface.ViewCartRequest(userId.value, storeId.value, productsInCart)

                val response = repository.viewCart(userId.value,storeId.value,viewCartRequest.products)

                Log.d("bhcxbh", response.toString())

                if (response?.status == true) {
                    toast(response.message)
                    navigation {
                        navigate(Routes.reviewCart.full)
                    }
                }
            } else {
                Log.d("bhcxbh", "No products with quantity greater than 0 to send to the server")
            }
        }
    }






    private fun selectDefaultBrand() {
        onBrandChange()
    }

    private fun onBrandChange() {
        val selectedBrandIndex = selectedBrandTabId.value
        if (selectedBrandIndex in brands.indices) {
            val selectedBrand = brands[selectedBrandIndex]
            val productBrandId = selectedBrand.uid ?: ""
            brandId.value = productBrandId
        }
    }

    private fun refreshCategories() {
        val currentBrand = brands.getOrNull(selectedBrandTabId.value)
        if (currentBrand == AllBrandDataResponse.All) {
            loadAllCategories()
        } else {
            categories.clear()
            loadAllCategories()
            if (!categories.any { it.uid == AllCategory.All.uid }) {
                categories.add(AllCategory.All)
            }
        }
        selectedCategoryTabId.value = AllCategory.All.uid
        onCategoryChange()
    }

    private fun onCategoryChange() {
        Log.d("SelectedCategoryId", selectedCategoryTabId.value)
        filterProducts()
    }

    private fun filterProducts() {
        filterListOfProducts.clear()
        filterListOfProducts.addAll(listOfProducts.filter { productFilter(it) })
    }

    private fun productFilter(p: Product): Boolean {
        if (selectedBrandTabId.value < 0) {
            return false
        }
        val currentBrand = brands.getOrNull(selectedBrandTabId.value)
        val currentCategory = categories.firstOrNull { it.uid == selectedCategoryTabId.value }

        val brandWiseAllowance =
            (currentBrand == AllBrandDataResponse.All) || (p.uid != currentBrand?.uid)
        if (!brandWiseAllowance) {
            return false
        }

        val categoryWiseAllowance =
            (currentCategory == AllCategory.All) || (p.uid != currentCategory?.uid)
        if (!categoryWiseAllowance) {
            return false
        }

        val queryLowerCase = searchProductQuery.value.toLowerCase()

        if (queryLowerCase.isBlank()) {
            return true
        }

        val productAttributes =
            listOf(p.uid, p.name, p.weight, p.pcs, p.mrp, p.discount, p.sell_price, p.image)
        return productAttributes.any { attribute ->
            attribute.toString().toLowerCase().contains(queryLowerCase)
        }
    }



    private fun setup() {
        controller.resolver.addAll(
            MyDataIds.brands to brands,
            MyDataIds.categories to categories,
            MyDataIds.brandChange to selectedBrandTabId,
            MyDataIds.productSearch to searchProductQuery,
            MyDataIds.noOfItem to noOfItem,
            MyDataIds.selectedCategoryId to selectedCategoryTabId,
            MyDataIds.filterProductData to filterListOfProducts,
            MyDataIds.lostInternet to lostInternet,
            MyDataIds.loadingState to loadingState,
            MyDataIds.emptyDataDialog to emptyDataDialog,
        )
    }
}

sealed class Matcher {
    abstract fun match(left: String, right: String): Boolean

    data class ContainsMatcher(val caseSensitive: Boolean) : Matcher() {
        override fun match(left: String, right: String): Boolean {
            return if (caseSensitive) left.contains(right) else left.contains(right, true)
        }
    }

    data class CommonSubstringMatcher(val caseSensitive: Boolean) : Matcher() {
        override fun match(left: String, right: String): Boolean {
            return hasCommonSubstring(left, right, caseSensitive)
        }
    }
}

fun hasCommonSubstring(string1: String, string2: String, caseSensitive: Boolean): Boolean {
    val str1 = if (caseSensitive) string1 else string1.toLowerCase()
    val str2 = if (caseSensitive) string2 else string2.toLowerCase()

    for (i in str1.indices) {
        for (j in i + 1 until str1.length + 1) {
            val substring = str1.substring(i, j)
            if (str2.contains(substring, true)) {
                return true
            }
        }
    }
    return false
}