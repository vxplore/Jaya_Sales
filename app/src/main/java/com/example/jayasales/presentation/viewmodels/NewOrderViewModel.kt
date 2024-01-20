package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.jayasales.di.NoConnectivityException
import com.example.jayasales.model.AllBrandDataResponse
import com.example.jayasales.model.AllCategory
import com.example.jayasales.model.Product
import com.example.jayasales.repository.Repository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewOrdersViewModel @Inject constructor(
    private val repository: Repository
) : WirelessViewModel() {
    private val brands = mutableStateListOf<AllBrandDataResponse.Brand>()
    private val categories = mutableStateListOf<AllCategory.Category>()
    private val selectedBrandTabId = mutableIntStateOf(0)
    private val selectedCategoryTabId = mutableStateOf("")
    private val searchProductQuery = mutableStateOf("")
    private val noOfItem = mutableIntStateOf(0)
    private val filterListOfProducts = mutableStateListOf<Product>()
    private val listOfProducts = mutableStateListOf<Product>()
    private val brandEndPoint = mutableStateOf("sells/product_brands")
    private val categoryEndPoint = mutableStateOf("sells/product_categories")
    private val brandId = mutableStateOf("")
    private val categoryId = mutableStateOf("")
    private val productId = mutableStateOf("")
    private val userId = mutableStateOf("")
    private val storeId = mutableStateOf("")
    private val quantity = mutableStateOf("")
    private val index = mutableStateOf(0)
    private val lostInternet = mutableStateOf(false)
    private val loadingState = mutableStateOf(false)
    private val emptyDataDialog = mutableStateOf(false)

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
                navigation {
                    popBackStack()
                }
            }

            MyDataIds.orderIndex -> {
                index.value = arg as Int
            }

            MyDataIds.viewToCart -> {
                viewCart()
                navigation {
                    navigate(Routes.reviewCart.full)
                }
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

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    var job: Job? = null
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
        //loadAllCategories()
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
                        val brands = response.data[index.value].uid
                        repository.setBrand(brands)
                        Log.d("dcdsgcx", brands)
                    }
                    //loadAllCategories()
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
                        val categories = matchingCategory?.uid ?: ""
                        repository.setCategory(categories)
                        Log.d("dcdscx", categories)
                    }
                    fetchProducts()
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
                    // Only update brandId and categoryId if not "All"
                    if (selectedBrandTabId.value != 0) {
                        productbrandId =
                            brands.getOrNull(selectedBrandTabId.value)?.uid ?: "NoBrandSelected"
                        Log.d("swsd", productbrandId)
                        val selectedBrandIndex = selectedBrandTabId.value
                        if (selectedBrandIndex in brands.indices) {
                            val selectedBrand = brands[selectedBrandIndex]
                            val productBrandId = selectedBrand.uid ?: "NoBrandSelected"
                            Log.d("SelectedBrandId", productBrandId)

                            val productbrandId =
                                brands.getOrNull(selectedBrandTabId.value)?.uid ?: "NoBrandSelected"
                            brandId.value = productBrandId
                            Log.d("swsd", brandId.value)// Update brandId here
                        }
                    } else {

                        productbrandId = brands.getOrNull(selectedBrandTabId.value)?.uid
                            ?: "" // Set a default value if brandId is "All"
                        brandId.value = ""
                        Log.d("swsd", brandId.value)// Update brandId here
                    }
                    if (selectedCategoryTabId.value != AllCategory.All.uid) {
                        categoryId.value = selectedCategoryTabId.value
                        Log.d("swsd", categoryId.value)
                    } else {
                        categoryId.value = ""
                    }
                    // Fetch products based on brand and category
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
                        filterProducts()
                        selectDefaultBrand()
                        if (list.isEmpty()) {
                            Log.d("fetchProducts", "No data available")
                            // Handle the case when data is empty, for example, show a message
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
            userId.value = "USER_78u88isit6yhadolutedd"
            storeId.value = repository.getUId()!!
            productId.value = repository.getProductId()!!

            val response =
                repository.viewCart(userId.value, storeId.value, productId.value, quantity.value)

            Log.d("fdvf", productId.value)
            Log.d("fdvf", quantity.value)

            if (response?.status == true) {
                toast(response.message)
            }
        }
    }

    private fun selectDefaultBrand() {
        //selectedBrandTabId.intValue = 0
        onBrandChange()
    }

    private fun onBrandChange() {
        val selectedBrandIndex = selectedBrandTabId.value
        if (selectedBrandIndex in brands.indices) {
            val selectedBrand = brands[selectedBrandIndex]
            val productBrandId = selectedBrand.uid ?: "NoBrandSelected"
            Log.d("SelectedBrandId", productBrandId)

            val productbrandId =
                brands.getOrNull(selectedBrandTabId.value)?.uid ?: "NoBrandSelected"
            Log.d("SelectedBrandId", productbrandId)
            /*  Log.d(
            "SelectedBrandId",
            brands.getOrNull(selectedBrandTabId.value)?.uid ?: "NoBrandSelected"
        )*/
            //filterProducts()
            //refreshCategories()
            //onCategoryChange()
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


    private fun matcher(): Matcher {
        return Matcher.CommonSubstringMatcher
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

    data object ContainsMatcher : Matcher() {
        override fun match(left: String, right: String): Boolean {
            return left.contains(right, true)
        }
    }

    data object CommonSubstringMatcher : Matcher() {
        override fun match(left: String, right: String): Boolean {
            return hasCommonSubstring(left, right)
        }
    }
}


fun hasCommonSubstring(string1: String, string2: String): Boolean {
    for (i in string1.indices) {
        for (j in i + 1 until string1.length + 1) {
            val substring = string1.substring(i, j)
            if (string2.contains(substring, true)) {
                return true
            }
        }
    }
    return false
}