package com.example.jayasales.repository.preference

import android.content.Context
import android.content.SharedPreferences
import com.example.jayasales.model.LoginDataResponse
import javax.inject.Inject


class PrefRepositoryImpl @Inject constructor(
    context : Context
): PrefRepository {

    private val isLoggedInKey="isLoggedInKey"
    private val userIdKey="userIdKey"
    private val uIdKey="uIdKey"
    private val logUIdKey="logUIdKey"
    private val logEmailKey="logEmailKey"
    private val brandKey="brandKey"
    private val categoryKey="categoryKey"
    private val orderKey="orderKey"
    private val productKey="productKey"
    private val cartIdKey = "cartIdKey"

    private lateinit var myPref : SharedPreferences
    init {
        myPref=context.getSharedPreferences("myPref", Context.MODE_PRIVATE)
    }

    override fun setIsLoggedIn(isLoggedIn: Boolean) {
        myPref.edit().putBoolean(isLoggedInKey,isLoggedIn).apply()
    }

    override fun getIsLoggedIn(): Boolean {
        return  myPref.getBoolean(isLoggedInKey,false)
    }

    override fun setUserId(userId: String?) {
        myPref.edit().putString(userIdKey, userId.toString()).apply()
    }

    override fun getUserId(): String? {
        return myPref.getString(userIdKey,"")
    }

    override fun setUId(uId: String?) {
      myPref.edit().putString(uIdKey,uId.toString()).apply()
    }

    override fun getBrand(): String? {
        return myPref.getString(brandKey,"")
    }

    override fun setBrand(brand: String?) {
        myPref.edit().putString(brandKey,brand.toString()).apply()
    }

    override fun getCategory(): String? {
        return myPref.getString(categoryKey,"")
    }

    override fun setCategory(category: String?) {
       myPref.edit().putString(categoryKey,category.toString()).apply()
    }

    override fun getOrderId(): String? {
        return myPref.getString(orderKey,"")
    }

    override fun setOrderId(orderId: String) {
        myPref.edit().putString(orderKey,orderId.toString()).apply()
    }

    override fun getCartId(): String? {
        return myPref.getString(cartIdKey,"")
    }

    override fun setCartId(cartId: String) {
       myPref.edit().putString(cartIdKey,cartId.toString()).apply()
    }

    override fun getProductId(): String? {
        return myPref.getString(productKey,"")
    }

    override fun setProductId(productId: String) {
        myPref.edit().putString(productKey,productId.toString()).apply()
    }

    override fun getUId(): String? {
      return myPref.getString(uIdKey,"")
    }

    override fun setLogUId(logUId: String?) {
        myPref.edit().putString(logUIdKey,logUId.toString()).apply()
    }

    override fun getLogUId(): String? {
       return myPref.getString(logUIdKey,"")
    }

    override fun setLogEmail(logEmail: String?) {
        myPref.edit().putString(logEmailKey,logEmail.toString()).apply()
    }

    override fun getLogEmail(): String? {
        return myPref.getString(logEmailKey,"")
    }

    override fun deleteUserId() {
        myPref.edit().remove(userIdKey).apply()
    }

}