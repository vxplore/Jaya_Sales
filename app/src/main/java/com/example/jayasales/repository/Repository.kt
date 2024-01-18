package com.example.jayasales.repository

import com.example.jayasales.model.AddStoreDataResponse
import com.example.jayasales.model.AllBrandDataResponse
import com.example.jayasales.model.AllCategory
import com.example.jayasales.model.AllProduct
import com.example.jayasales.model.AttendanceDataResponse
import com.example.jayasales.model.CheckInOutDataResponse
import com.example.jayasales.model.GetOtpResponse
import com.example.jayasales.model.LoginDataResponse
import com.example.jayasales.model.MarkVisitDataResponse
import com.example.jayasales.model.PartiesDataResponse
import com.example.jayasales.model.PaymentInList
import com.example.jayasales.model.PlaceOrderDataResponse
import com.example.jayasales.model.ReceivePaymentInList
import com.example.jayasales.model.ResetDataResponse
import com.example.jayasales.model.ReviewCartDataResponse
import com.example.jayasales.model.RouteDataResponse
import com.example.jayasales.model.SearchRouteDataResponse
import com.example.jayasales.model.StoreDetailsDataResponse
import com.example.jayasales.model.TimeSheetDataResponse
import com.example.jayasales.model.ViewCartDataResponse
import java.io.File

interface Repository {


    suspend fun login(email: String, password: String): LoginDataResponse?
    suspend fun getOtp(email: String): GetOtpResponse?
    suspend fun route(data: String): RouteDataResponse?
    suspend fun timeSheet(userId: String): TimeSheetDataResponse?
    suspend fun attendance(data: String): AttendanceDataResponse?
    suspend fun searchRoute(query: String): SearchRouteDataResponse?
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
    suspend fun viewCart(
        userId: String,
        storeId: String,
        productId: String,
        quantity: String,
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

    fun getIsLoggedIn(): Boolean

    fun saveUser(userId: LoginDataResponse?)

    fun getUserId(): String?
    fun getUId(): String?
    fun setUId(UId: String?)
    fun getBrand(): String?
    fun setBrand(brand: String?)
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

    fun getLogEmail(): String?
    fun setLogEmail(logEmail: String?)

    fun removeUser()

    suspend fun resetPassword(
        recoverUsername: String,
        confirmPassword: String,
        otp: String
    ): ResetDataResponse?

    suspend fun addCustomer(
        storeName: String,
        image: File,
        cityId: String,
        stateId: String,
        postalCode: Int,
        address: String,
        routeId: String,
        gpsLocation: String,
        contactName: String,
        contactNumber: Int,
        contactEmail: String,
        gst: String
    ): AddStoreDataResponse?

}