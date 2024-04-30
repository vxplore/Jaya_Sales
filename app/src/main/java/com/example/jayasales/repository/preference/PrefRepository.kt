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
    fun getReturnProductId(): String?
    fun setReturnProductId(returnProductId: String?)
    fun getReasonId(): String?
    fun setReasonId(reasonId: String?)
    fun getReturnStoreId(): String?
    fun setReturnStoreId(returnStoreId: String?)
    fun getCategoryId(): String?
    fun setCategoryId(categoryId: String?)
    fun getReturnBrand(): String?
    fun setReturnBrand(returnBrand: String?)
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
    fun getAddStoreId(): String?
    fun setAddStoreId(addStoreId: String?)

    fun getUId(): String?

    fun setLogUId(logUId: String?)

    fun getLogUId(): String?

    fun setLogEmail(logEmail: String?)

    fun getLogEmail(): String?


    fun getUserType(): String?
    fun setUserType(userType: String?)

    fun setUpdateProductId(updateProductId:String)
    fun getUpdateProductId() : String?



    fun setDistributorOrderId(distributorOrderId: String?)

    fun getDistributorOrderId(): String?

    fun deleteUserId()
}