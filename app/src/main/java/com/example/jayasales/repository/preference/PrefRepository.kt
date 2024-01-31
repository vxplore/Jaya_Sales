package com.example.jayasales.repository.preference


interface PrefRepository {

    fun setIsLoggedIn(isLoggedIn: Boolean)

    fun getIsLoggedIn(): Boolean

    fun setUserId(userId: String?)

    fun getUserId(): String?
    fun setUId(userId: String?)
    fun getBrand(): String?
    fun setBrand(brand: String?)
    fun getRouteId(): String?
    fun setRouteId(routeId: String?)
    fun getRouteName(): String?
    fun setRouteName(routeName: String?)
    fun getCategory(): String?
    fun setCategory(category: String?)
    fun getState(): String?
    fun setState(state: String?)
    fun getAddRoute(): String?
    fun setAddRoute(addRoute: String?)
    fun getCity(): String?
    fun setCity(city: String?)
    fun getOrderId(): String?
    fun setOrderId(orderId: String)
    fun getCartId(): String?
    fun setCartId(cartId: String)
    fun getProductId(): String?
    fun setProductId(productId: String)

    fun getUId(): String?

    fun setLogUId(logUId: String?)

    fun getLogUId(): String?

    fun setLogEmail(logEmail: String?)

    fun getLogEmail(): String?

    fun deleteUserId()
}