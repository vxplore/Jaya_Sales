package com.example.jayasales.repository

import android.annotation.SuppressLint
import android.util.Log
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
import com.example.jayasales.model.ReceivePaymentInList
import com.example.jayasales.model.ResetDataResponse
import com.example.jayasales.model.RouteDataResponse
import com.example.jayasales.model.SearchRouteDataResponse
import com.example.jayasales.model.StoreDetailsDataResponse
import com.example.jayasales.model.TimeSheetDataResponse
import com.example.jayasales.presentation.viewmodels.PaymentModeTab
import com.example.jayasales.repository.preference.PrefRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import javax.inject.Inject


class MockRepositoryImpl @Inject constructor(
    private val myPref: PrefRepository,
    private val apiHelper: ApiInterface,
) : Repository {
    override suspend fun login(email: String, password: String): LoginDataResponse? {
        val response = apiHelper.Login(email, password)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun getOtp(email: String): GetOtpResponse? {
        val response = apiHelper.getOtp(email)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun route(data: String): RouteDataResponse? {
        val response = apiHelper.route(data)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun timeSheet(userId: String): TimeSheetDataResponse? {
        val response = apiHelper.timeSheet(userId)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun attendance(data: String): AttendanceDataResponse? {
       val response = apiHelper.attendance(data)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun searchRoute(query: String): SearchRouteDataResponse? {
        val response = apiHelper.searchRoute(query)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun parties(
        userId: String,
        routeId: String,
        searchText: String
    ): PartiesDataResponse? {
        val response = apiHelper.parties(userId,routeId,searchText)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun storeDetails(storeId: String, userId: String): StoreDetailsDataResponse? {
        val response = apiHelper.storeDetails(storeId,userId)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun searchParty(searchParty: String): PartiesDataResponse? {
        val response = apiHelper.searchParty(searchParty)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun markVisit(
        userId: String,
        storeId: String,
        lat: String,
        lng: String,
        comment: String
    ): MarkVisitDataResponse? {
        //val response = apiHelper.markVisit(userId,storeId,lat, lng, comment)
        val client = OkHttpClient()
        val mediaType = "text/plain".toMediaType()
        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("user_id","USER_78u88isit6yhadolutedd")
            .addFormDataPart("store_id","STORE_590645")
            .addFormDataPart("lat"," 22.6483178")
            .addFormDataPart("lng"," 88.341644")
            .addFormDataPart("comment","test comment")
            .build()
        val request = Request.Builder()
            .url("https://apis.jayaindustries.in/jayasalesapi/v1/sells/mark_visit")
            .post(body)
            .build()
        val response = client.newCall(request).execute()
        Log.d("dfuh",response.networkResponse.toString())
        return null
    }

    override suspend fun checkInOut(
        userId: String,
        lat: String,
        lng: String,
        comment: String,
        status: String
    ): CheckInOutDataResponse? {
        val response = apiHelper.checkInOut(userId,lat, lng, comment, status)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun resetPassword(
        recoverUsername: String,
        confirmPassword: String,
        otp: String
    ): ResetDataResponse? {
        val response = apiHelper.resetPassword(recoverUsername, confirmPassword, otp)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override suspend fun addCustomer(
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
    ): AddStoreDataResponse? {
        val response = apiHelper.addCustomer(
            storeName,
            image,
            cityId,
            stateId,
            postalCode,
            address,
            routeId,
            gpsLocation,
            contactName,
            contactNumber,
            contactEmail,
            gst
        )
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }


    override fun setIsLoggedIn(done: Boolean) {
        myPref.setIsLoggedIn(done)
    }

    override suspend fun allBrands(allBrands: String): AllBrandDataResponse? {
        val response = apiHelper.allBrands(allBrands)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun allCategory(allCategory: String): AllCategory? {
        val response = apiHelper.allCategories(allCategory)
       return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun allProducts(
        categoryId: String,
        brandId: String,
        searchText: String
    ): AllProduct? {
        val response = apiHelper.allProducts(categoryId, brandId, searchText)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun paymentIn(userId: String, storeId: String): PaymentInList? {
        val response = apiHelper.paymentIn(userId,storeId)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun receivePayment(
        userId: String,
        orderId: String,
        storeId: String,
        price: String,
        paymentType: PaymentModeTab,
        instruction: String
    ): ReceivePaymentInList? {
        val response = apiHelper.receivePayment(userId,orderId,storeId,price,paymentType,instruction)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override fun getIsLoggedIn(): Boolean {
        return myPref.getIsLoggedIn()
    }

    override fun saveUser(userId: LoginDataResponse?) {
        myPref.setUserId(userId)
    }

    override fun getUserId(): String? {
        return myPref.getUserId()
    }

    override fun getUId(): String? {
        return myPref.getUId()
    }

    override fun setUId(UId: String?) {
        myPref.setUId(UId)
    }

    override fun getBrand(): String? {
        return myPref.getBrand()
    }

    override fun setBrand(brand: String?) {
      myPref.setBrand(brand)
    }

    override fun getCategory(): String? {
        return myPref.getCategory()
    }

    override fun setCategory(category: String?) {
       myPref.setCategory(category)
    }

    override fun getOrderId(): String? {
       return myPref.getOrderId()
    }

    override fun setOrderId(orderId: List<String>) {
        myPref.setOrderId(orderId)
    }


    override fun getLogUId(): String? {
        return myPref.getLogUId()
    }

    override fun setLogUId(logUId: String?) {
        myPref.setLogUId(logUId)
    }

    override fun getLogEmail(): String? {
        return myPref.getLogEmail()
    }

    override fun setLogEmail(logEmail: String?) {
       myPref.setLogEmail(logEmail)
    }

    override fun removeUser() {
        myPref.deleteUserId()
    }
}