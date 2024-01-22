package com.example.jayasales

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        AppContext.init(this)
    }

    companion object{
        val products: Products by lazy { Products() }
        val cart: Cart by lazy { Cart() }
    }
}
object AppContext {
    private lateinit var _app: App
    val app: App get() = _app
    fun init(app: App) {
        _app = app
    }
}


data class Product(
    val id: String,
)
class Products{
    private val products = mutableMapOf<String,Product>()
    fun saveProducts(products: List<Product>){
        this.products.clear()
        this.products.putAll(
            products.associateBy { it.id }
        )
    }
    fun addProduct(product: Product){
        products[product.id] = product
    }
    fun removeProduct(product: Product){
        products.remove(product.id)
    }
    fun getProducts(): List<Product>{
        return products.values.toList()
    }
}

class Cart {
    private val productQuantity = mutableMapOf<String, Int>()
    fun add(id: String, quantity: Int){
        val prevQuantity = productQuantity[id]?:0
        val newQuantity = (prevQuantity + quantity).coerceAtLeast(0)
        if(newQuantity==0){
            productQuantity.remove(id)
            return
        }
        productQuantity[id] = newQuantity
    }

    fun get(id: String): Int{
        return productQuantity[id]?:0
    }

    fun remove(id: String){
        productQuantity.remove(id)
    }

    fun get(): Map<String,Int>{
        return productQuantity
    }

    fun clear(){
        productQuantity.clear()
    }
}