package com.example.jayasales.repository

import android.util.Log
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
import com.example.jayasales.repository.preference.PrefRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun attendance(data: String): AttendanceDataResponse? {
        val response = apiHelper.attendance(data)
        return if (response.isSuccessful) {
            response.body()
        } else {
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

    override suspend fun state(states: String): StateDataResponse? {
        val response = apiHelper.state(states)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun city(city: String): CityDataResponse? {
        val response = apiHelper.city(city)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun reason(reason: String): ReasonDataResponse? {
        val response = apiHelper.reason(reason)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun dashboard(userId: String): DashboardDataResponse? {
        val response = apiHelper.dashboard(userId)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun parties(
        userId: String,
        routeId: String,
        searchText: String
    ): PartiesDataResponse? {
        val response = apiHelper.parties(userId, routeId, searchText)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun storeDetails(storeId: String, userId: String): StoreDetailsDataResponse? {
        val response = apiHelper.storeDetails(storeId, userId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun reviewCart(userId: String, storeId: String): ReviewCartDataResponse? {
        val response = apiHelper.reviewCart(userId, storeId)
        return if (response.isSuccessful) {
            response.body()
        } else {
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
        val response = apiHelper.markVisit(userId, storeId, lat, lng, comment)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun remove(userId: String, storeId: String, cartId: String): RemoveResponse? {
        val response = apiHelper.remove(userId,storeId,cartId)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun viewCart(
        userId: String,
        storeId: String,
        products: List<AllProducts>
    ): ViewCartDataResponse? {
        val response = apiHelper.viewCart(ApiInterface.ViewCartRequest(userId, storeId, products))
        return if (response.isSuccessful){
            response.body()
        }else{
            Log.e("Repository", "Error: ${response.code()}")
            null
        }
    }

    override suspend fun checkInOut(
        userId: String,
        lat: String,
        lng: String,
        comment: String,
        status: String
    ): CheckInOutDataResponse? {
        val response = apiHelper.checkInOut(userId, lat, lng, comment, status)
        return if (response.isSuccessful) {
            response.body()
        } else {
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


    override fun setIsLoggedIn(done: Boolean) {
        myPref.setIsLoggedIn(done)
    }

    override suspend fun allBrands(allBrands: String): AllBrandDataResponse? {
        val response = apiHelper.allBrands(allBrands)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun allCategory(allCategory: String): AllCategory? {
        val response = apiHelper.allCategories(allCategory)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun allProducts(
        categoryId: String,
        brandId: String,
        searchText: String
    ): AllProduct? {
        val response = apiHelper.allProducts(categoryId, brandId, searchText)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun paymentIn(userId: String, storeId: String): PaymentInList? {
        val response = apiHelper.paymentIn(userId, storeId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun placeOrder(userId: String, storeId: String): PlaceOrderDataResponse? {
        val response = apiHelper.placeOrder(userId, storeId)
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
        paymentType: String,
        instruction: String
    ): ReceivePaymentInList? {
        val response =
            apiHelper.receivePayment(userId, orderId, storeId, price, paymentType, instruction)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun updateStore(
        userId: String,
        storeId: String,
        owner: String,
        phone: String,
        email: String,
        gst: String
    ): UpdateStoreDataResponse? {
        val response = apiHelper.updateStore(userId,storeId,owner,phone,email,gst)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun deleteStore(userId: String, storeId: String): DeleteStoreDataResponse? {
        val response = apiHelper.deleteStore(userId,storeId)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }


    override suspend fun addStore(
        images: MultipartBody.Part,
        userId: String,
        storeName: String,
        cityId: String,
        stateId: String,
        postalCode: String,
        address: String,
        routeId: String,
        lat: String,
        lng: String
    ): AddStoreDataResponse? {
        val requestFile: RequestBody? = images.body
        val multipartImage = requestFile?.let {
            MultipartBody.Part.createFormData("images[]", images.toString(), it)
        }
        fun String.requestBody(): RequestBody {
            return RequestBody.create("text/plain".toMediaTypeOrNull(), this)
        }

        val response = apiHelper.addStore(
            multipartImage!!,
            userId.requestBody(),
            storeName.requestBody(),
            cityId.requestBody(),
            stateId.requestBody(),
            postalCode.requestBody(),
            address.requestBody(),
            routeId.requestBody(),
            lat.requestBody(),
            lng.requestBody()
        )
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun returnRequest(
        userId: String,
        storeId: String,
        productId: String,
        categoryId: String,
        brandId: String,
        packedOn: String,
        lot: String,
        reasonId: String,
        message: String,
        images: MultipartBody.Part
    ): ReturnRequestDataResponse? {
        val requestFile: RequestBody? = images.body
        val multipartImage = requestFile?.let {
            MultipartBody.Part.createFormData("images[]", images.toString(), it)
        }
        fun String.requestBody(): RequestBody {
            return RequestBody.create("text/plain".toMediaTypeOrNull(), this)
        }
        val response = apiHelper.returnRequest(
            userId.requestBody(),
            storeId.requestBody(),
            productId.requestBody(),
            categoryId.requestBody(),
            brandId.requestBody(),
            packedOn.requestBody(),
            lot.requestBody(),
            reasonId.requestBody(),
            message.requestBody(),
            multipartImage!!
        )
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }


    override fun getIsLoggedIn(): Boolean {
        return myPref.getIsLoggedIn()
    }

    override fun saveUser(userId: String?) {
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

    override fun getAddRoute(): String? {
        return myPref.getAddRoute()
    }

    override fun setAddRoute(addRoute: String?) {
       myPref.setAddRoute(addRoute)
    }

    override fun getCity(): String? {
        return myPref.getCity()
    }

    override fun setCity(city: String?) {
        myPref.setCity(city)
    }

    override fun getState(): String? {
        return myPref.getState()
    }

    override fun setState(state: String?) {
       myPref.setState(state)
    }

    override fun getReturnProductId(): String? {
        return myPref.getReturnProductId()
    }

    override fun setReturnProductId(returnProductId: String?) {
        myPref.setReturnProductId(returnProductId)
    }

    override fun getReasonId(): String? {
        return myPref.getReasonId()
    }

    override fun setReasonId(reasonId: String?) {
       myPref.setReasonId(reasonId)
    }

    override fun getReturnStoreId(): String? {
        return myPref.getReturnStoreId()
    }

    override fun setReturnStoreId(returnStoreId: String?) {
        myPref.setReturnStoreId(returnStoreId)
    }

    override fun getCategoryId(): String? {
        return myPref.getCategoryId()
    }

    override fun setCategoryId(categoryId: String?) {
        myPref.setCategoryId(categoryId)
    }

    override fun getReturnBrand(): String? {
        return myPref.getReturnBrand()
    }

    override fun setReturnBrand(returnBrand: String?) {
       myPref.setReturnBrand(returnBrand)
    }

    override fun getRouteId(): String? {
        return myPref.getRouteId()
    }

    override fun setRouteId(routeId: String?) {
        myPref.setRouteId(routeId)
    }

    override fun getRouteName(): String? {
        return myPref.getRouteName()
    }

    override fun setRouteName(routeName: String?) {
       myPref.setRouteName(routeName)
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

    override fun setOrderId(orderId: String) {
        myPref.setOrderId(orderId)
    }

    override fun getCartId(): String? {
        return myPref.getCartId()
    }

    override fun setCartId(cartId: String) {
       myPref.setCartId(cartId)
    }

    override fun getProductId(): String? {
        return myPref.getProductId()
    }

    override fun setProductId(productId: String) {
       myPref.setProductId(productId)
    }


    override fun getLogUId(): String? {
        return myPref.getLogUId()
    }

    override fun setLogUId(logUId: String?) {
        myPref.setLogUId(logUId)
    }

    override fun getAddStoreId(): String? {
        return myPref.getAddStoreId()
    }

    override fun setAddStoreId(addStoreId: String?) {
        myPref.setAddStoreId(addStoreId)
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