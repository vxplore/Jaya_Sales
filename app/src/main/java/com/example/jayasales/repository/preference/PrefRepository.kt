package com.example.jayasales.repository.preference

import com.example.jayasales.model.LoginDataResponse

interface PrefRepository {

    fun setIsLoggedIn(isLoggedIn: Boolean)

    fun getIsLoggedIn(): Boolean

    fun setUserId(userId: LoginDataResponse?)

    fun getUserId(): String?
    fun setUId(userId: String?)
    fun getBrand(): String?
    fun setBrand(brand: String?)
    fun getCategory(): String?
    fun setCategory(category: String?)

    fun getOrderId(): String?
    fun setOrderId(orderId: List<String>)

    fun getUId(): String?

    fun setLogUId(logUId: String?)

    fun getLogUId(): String?

    fun setLogEmail(logEmail: String?)

    fun getLogEmail(): String?

    fun deleteUserId()
}