package com.example.jayasales.repository

import com.example.jayasales.model.AddStoreDataResponse
import com.example.jayasales.model.AllBrandDataResponse
import com.example.jayasales.model.AllCategory
import com.example.jayasales.model.AllProduct
import com.example.jayasales.model.AllProducts
import com.example.jayasales.model.AttendanceDataResponse
import com.example.jayasales.model.CheckInOutDataResponse
import com.example.jayasales.model.CityDataResponse
import com.example.jayasales.model.DashboardDataResponse
import com.example.jayasales.model.DeleteStoreDataResponse
import com.example.jayasales.model.GetOtpResponse
import com.example.jayasales.model.LoginDataResponse
import com.example.jayasales.model.MarkVisitDataResponse
import com.example.jayasales.model.PartiesDataResponse
import com.example.jayasales.model.PaymentInList
import com.example.jayasales.model.PlaceOrderDataResponse
import com.example.jayasales.model.ReasonDataResponse
import com.example.jayasales.model.ReceivePaymentInList
import com.example.jayasales.model.RemoveResponse
import com.example.jayasales.model.ResetDataResponse
import com.example.jayasales.model.ReturnRequestDataResponse
import com.example.jayasales.model.ReviewCartDataResponse
import com.example.jayasales.model.RouteDataResponse
import com.example.jayasales.model.SearchRouteDataResponse
import com.example.jayasales.model.StateDataResponse
import com.example.jayasales.model.StoreDetailsDataResponse
import com.example.jayasales.model.TimeSheetDataResponse
import com.example.jayasales.model.UpdateStoreDataResponse
import com.example.jayasales.model.ViewCartDataResponse
import okhttp3.MultipartBody

interface Repository {


    suspend fun login(email: String, password: String): LoginDataResponse?
    suspend fun getOtp(email: String): GetOtpResponse?
    suspend fun route(data: String): RouteDataResponse?
    suspend fun timeSheet(userId: String): TimeSheetDataResponse?
    suspend fun attendance(data: String): AttendanceDataResponse?
    suspend fun searchRoute(query: String): SearchRouteDataResponse?
    suspend fun state(states: String): StateDataResponse?
    suspend fun city(city: String): CityDataResponse?
    suspend fun reason(reason: String): ReasonDataResponse?
    suspend fun dashboard(userId: String): DashboardDataResponse?
    suspend fun parties(userId: String, routeId: String, searchText: String): PartiesDataResponse?
    suspend fun storeDetails(storeId: String, userId: String): StoreDetailsDataResponse?
    suspend fun reviewCart(userId: String, storeId: String): ReviewCartDataResponse?
    suspend fun searchParty(searchParty: String): PartiesDataResponse?
    suspend fun markVisit(
        userId: String,
        storeId: String,
        lat: String,
        lng: String,
        comment: String
    ): MarkVisitDataResponse?
    suspend fun remove(
        userId: String,
        storeId: String,
        cartId: String,
    ): RemoveResponse?
    suspend fun viewCart(
        userId: String,
        storeId: String,
        products: List<AllProducts>
    ): ViewCartDataResponse?

    suspend fun checkInOut(
        userId: String,
        lat: String,
        lng: String,
        comment: String,
        status: String
    ): CheckInOutDataResponse?

    fun setIsLoggedIn(done: Boolean)
    suspend fun allBrands(allBrands: String): AllBrandDataResponse?
    suspend fun allCategory(allCategory: String): AllCategory?
    suspend fun allProducts(categoryId: String, brandId: String, searchText: String): AllProduct?
    suspend fun paymentIn(userId: String, storeId: String): PaymentInList?
    suspend fun placeOrder(userId: String, storeId: String): PlaceOrderDataResponse?
    suspend fun receivePayment(
        userId: String,
        orderId: String,
        storeId: String,
        price: String,
        paymentType: String,
        instruction: String
    ): ReceivePaymentInList?

    suspend fun updateStore(
        userId: String,
        storeId: String,
        owner: String,
        phone: String,
        email: String,
        gst: String
    ): UpdateStoreDataResponse?
    suspend fun deleteStore(
        userId: String,
        storeId: String,
    ): DeleteStoreDataResponse?

    suspend fun addStore(
        images: MultipartBody.Part,
        userId: String,
        storeName: String,
        cityId: String,
        stateId: String,
        postalCode: String,
        address: String,
        routeId: String,
        lat: String,
        lng: String,
    ): AddStoreDataResponse?

    suspend fun returnRequest(
        userId: String,
        storeId: String,
        productId: String,
        categoryId: String,
        brandId: String,
        packedOn: String,
        lot: String,
        reasonId: String,
        message: String,
        images: MultipartBody.Part,
    ): ReturnRequestDataResponse?


    fun getIsLoggedIn(): Boolean

    fun saveUser(userId: String?)

    fun getUserId(): String?
    fun getUId(): String?
    fun setUId(UId: String?)
    fun getBrand(): String?
    fun setBrand(brand: String?)
    fun getAddRoute(): String?
    fun setAddRoute(addRoute: String?)
    fun getCity(): String?
    fun setCity(city: String?)
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
    fun getRouteId(): String?
    fun setRouteId(routeId: String?)
    fun getRouteName(): String?
    fun setRouteName(routeName: String?)
    fun getCategory(): String?
    fun setCategory(category: String?)
    fun getOrderId(): String?
    fun setOrderId(orderId: String)
    fun getCartId(): String?
    fun setCartId(cartId: String)
    fun getProductId(): String?
    fun setProductId(productId: String)

    fun getLogUId(): String?
    fun setLogUId(logUId: String?)
    fun getAddStoreId(): String?
    fun setAddStoreId(addStoreId: String?)

    fun getLogEmail(): String?
    fun setLogEmail(logEmail: String?)

    fun removeUser()

    suspend fun resetPassword(
        recoverUsername: String,
        confirmPassword: String,
        otp: String
    ): ResetDataResponse?

}