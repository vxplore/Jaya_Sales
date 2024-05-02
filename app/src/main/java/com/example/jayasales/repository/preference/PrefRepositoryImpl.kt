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
    private val routeIdKey = "routeIdKey"
    private val routeNameKey = "routeNameKey"
    private val stateKey = "stateKey"
    private val addRouteKey = "addRouteKey"
    private val cityKey = "cityKey"
    private val addStoreIdKey = "addStoreIdKey"
    private val returnBrandKey = "returnBrandKey"
    private val categoryIdKey = "categoryIdKey"
    private val returnProductIdKey = "returnProductIdKey"
    private val returnStoreIdKey = "returnStoreIdKey"
    private val reasonIdKey = "reasonIdKey"
    private val DistributorOrderId = "reasonIdKey"
    private val UserType = "UserType"
    private val UpdateProductId = "UpdateProductId"
    private val Lat = "Lat"
    private val Lng = "Lng"
    private val CallNo = "CallNo"
    private val SalesmenId = "SalesmenId"



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

    override fun getRouteId(): String? {
        return myPref.getString(routeIdKey,"")
    }

    override fun setRouteId(routeId: String?) {
       myPref.edit().putString(routeIdKey,routeId.toString()).apply()
    }

    override fun getRouteName(): String? {
        return myPref.getString(routeNameKey,"")
    }

    override fun setRouteName(routeName: String?) {
        myPref.edit().putString(routeNameKey,routeName.toString()).apply()
    }

    override fun getCategory(): String? {
        return myPref.getString(categoryKey,"")
    }

    override fun setCategory(category: String?) {
       myPref.edit().putString(categoryKey,category.toString()).apply()
    }

    override fun getState(): String? {
        return myPref.getString(stateKey,"")
    }

    override fun setState(state: String?) {
      myPref.edit().putString(stateKey,state.toString()).apply()
    }

    override fun getReturnProductId(): String? {
        return myPref.getString(returnProductIdKey,"")
    }

    override fun setReturnProductId(returnProductId: String?) {
       myPref.edit().putString(returnProductIdKey,returnProductId.toString()).apply()
    }

    override fun getReasonId(): String? {
        return myPref.getString(reasonIdKey,"")
    }

    override fun setReasonId(reasonId: String?) {
       myPref.edit().putString(reasonIdKey,reasonId.toString()).apply()
    }

    override fun getReturnStoreId(): String? {
        return myPref.getString(returnStoreIdKey,"")
    }

    override fun setReturnStoreId(returnStoreId: String?) {
        myPref.edit().putString(returnStoreIdKey,returnStoreId.toString()).apply()
    }

    override fun getCategoryId(): String? {
        return myPref.getString(categoryIdKey,"")
    }

    override fun setCategoryId(categoryId: String?) {
     myPref.edit().putString(categoryIdKey,categoryId.toString()).apply()
    }

    override fun getReturnBrand(): String? {
        return myPref.getString(returnBrandKey,"")
    }

    override fun setReturnBrand(returnBrand: String?) {
      myPref.edit().putString(returnBrandKey,returnBrand.toString()).apply()
    }

    override fun getAddRoute(): String? {
        return myPref.getString(addRouteKey,"")
    }

    override fun setAddRoute(addRoute: String?) {
       myPref.edit().putString(addRouteKey,addRoute.toString()).apply()
    }

    override fun getCity(): String? {
        return myPref.getString(cityKey,"")
    }

    override fun setCity(city: String?) {
     myPref.edit().putString(cityKey,city.toString()).apply()
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

    override fun getAddStoreId(): String? {
        return myPref.getString(addStoreIdKey,"")
    }

    override fun setAddStoreId(addStoreId: String?) {
       myPref.edit().putString(addStoreIdKey,addStoreId.toString()).apply()
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

    override fun getUserType(): String? {
        return myPref.getString(UserType,"")
    }

    override fun setUserType(userType: String?) {
        myPref.edit().putString(UserType,userType.toString()).apply()
    }

    override fun setUpdateProductId(updateProductId: String) {
        myPref.edit().putString(UpdateProductId,updateProductId.toString()).apply()
    }

    override fun getUpdateProductId(): String? {
       return myPref.getString(UpdateProductId,"")
    }

    override fun setlat(lat: String) {
        myPref.edit().putString(Lat,lat.toString()).apply()
    }

    override fun getlat(): String? {
        return myPref.getString(Lat,"")
    }

    override fun setlng(lng: String) {
        myPref.edit().putString(Lng,lng.toString()).apply()
    }

    override fun getlng(): String? {
        return myPref.getString(Lng,"")
    }

    override fun setCallNo(callNo: String) {
        myPref.edit().putString(CallNo,callNo.toString()).apply()
    }

    override fun getCallNo(): String? {
        return myPref.getString(CallNo,"")
    }

    override fun setSalesmenId(salesmenId: String) {
        myPref.edit().putString(SalesmenId,salesmenId.toString()).apply()
    }

    override fun getSalesmenId(): String? {
       return myPref.getString(SalesmenId,"")
    }

    override fun setDistributorOrderId(distributorOrderId: String?) {
        myPref.edit().putString(DistributorOrderId,distributorOrderId.toString()).apply()
    }

    override fun getDistributorOrderId(): String? {
        return myPref.getString(DistributorOrderId,"")
    }

    override fun deleteUserId() {
        myPref.edit().remove(userIdKey).apply()
    }

}