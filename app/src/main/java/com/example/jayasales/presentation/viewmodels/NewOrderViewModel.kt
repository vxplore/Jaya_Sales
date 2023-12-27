package com.example.jayasales.presentation.viewmodels


import android.os.Bundle
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import com.example.jayasales.Routes
import com.example.jayasales.model.Brand
import com.example.jayasales.model.Category
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

    private val brands = mutableStateListOf<Brand>()
    private val categories = mutableStateListOf<Category>()
    private val selectedBrandTabId = mutableIntStateOf(-1)
    private val selectedCategoryTabId = mutableStateOf("")
    private val searchProductQuery = mutableStateOf("")
    private val noOfItem = mutableIntStateOf(0)
    private val filterListOfProducts = mutableStateListOf<Product>()
    private val listOfProducts = mutableStateListOf<Product>()
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

            MyDataIds.viewToCart -> {
                navigation {
                    navigate(Routes.reviewCart.full)
                }
            }

            MyDataIds.brandChange -> {
                selectedBrandTabId.value = arg as Int
                onBrandChange()
            }

            MyDataIds.productSearch -> {
                searchProductQuery.value = arg as String
                searchLatch(500) {
                    filterProducts()
                }
            }

            MyDataIds.productQuantityMinus -> {
                if (noOfItem.value > 0) {
                    noOfItem.value -= 1
                }
            }

            MyDataIds.productQuantityPlus -> {
                noOfItem.value += 1
            }

            MyDataIds.categoryChange -> {
                selectedCategoryTabId.value = arg as String
                filterProducts()
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
    }

    private fun fetchBrands() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.allBrands()
            withContext(Dispatchers.Main) {
                brands.clear()
                brands.add(Brand.All)
                brands.addAll(list)
                loadAllCategories()
                fetchProducts()
            }
        }
    }

    private fun loadAllCategories() {
        categories.clear()
        categories.add(Category.All)
        brands.forEach {
            categories.addAll(it.categories)
            categories.sortBy {
                it.name
            }
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.allProducts()
            withContext(Dispatchers.Main) {
                listOfProducts.clear()
                listOfProducts.addAll(list)
                withContext(Dispatchers.Main) {
                    filterProducts()
                }
                selectDefaultBrand()
            }
        }
    }

    private fun selectDefaultBrand() {
        selectedBrandTabId.intValue = 0
        onBrandChange()
    }

    private fun onBrandChange() {
        refreshCategories()
    }

    private fun refreshCategories() {
        val currentBrand = brands[selectedBrandTabId.intValue]
        if (currentBrand == Brand.All) {
            loadAllCategories()
        } else {
            categories.clear()
            categories.add(Category.All)
            categories.addAll(currentBrand.categories)
        }
        selectedCategoryTabId.value = Category.All.id
        onCategoryChange()
    }

    private fun onCategoryChange() {
        filterProducts()
    }

    private fun filterProducts() {
        filterListOfProducts.clear()
        filterListOfProducts.addAll(listOfProducts.filter {
            productFilter(it)
        })
    }

    private fun productFilter(p: Product): Boolean {
        if (selectedBrandTabId.intValue < 0) {
            return false
        }
        val currentBrand = brands[selectedBrandTabId.intValue]
        val currentCategory = categories.first { it.id == selectedCategoryTabId.value }

        val brandWiseAllowance =
            (currentBrand == Brand.All) || (p.brandDetails.id == currentBrand.id)
        if (!brandWiseAllowance) {
            return false
        }

        val categoryWiseAllowance =
            (currentCategory == Category.All) || (p.categoryDetails.id == currentCategory.id)
        if (!categoryWiseAllowance) {
            return false
        }
        if (searchProductQuery.value.isEmpty()) {
            return true
        }
        val namesToSearchIn = p.names
        return namesToSearchIn.any {
            matcher().match(it, searchProductQuery.value)
        }
    }

    private fun matcher(): Matcher {
        return Matcher.CommonSubstringMatcher
    }

    private fun setup() {
        controller.resolver.addAll(
            MyDataIds.brands to brands,

            MyDataIds.brandChange to selectedBrandTabId,
            MyDataIds.productSearch to searchProductQuery,
            MyDataIds.categories to categories,
            MyDataIds.noOfItem to noOfItem,
            MyDataIds.selectedCategoryId to selectedCategoryTabId,
            MyDataIds.filterProductData to filterListOfProducts
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