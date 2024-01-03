package com.example.jayasales.repository

import com.example.jayasales.model.AddStoreDataResponse
import com.example.jayasales.model.Brand
import com.example.jayasales.model.GetOtpResponse
import com.example.jayasales.model.LoginDataResponse
import com.example.jayasales.model.PartiesDataResponse
import com.example.jayasales.model.Product
import com.example.jayasales.model.ResetDataResponse
import com.example.jayasales.model.RouteDataResponse
import com.example.jayasales.model.SearchRouteDataResponse
import java.io.File

interface Repository {


    suspend fun login(email : String, password : String) : LoginDataResponse?
    suspend fun getOtp(email: String) : GetOtpResponse?
    suspend fun route(data:String) : RouteDataResponse?
    suspend fun searchRoute(query:String) : SearchRouteDataResponse?
    suspend fun parties(parties:String) : PartiesDataResponse?
    suspend fun searchParty(searchParty:String) : PartiesDataResponse?
    fun setIsLoggedIn(done : Boolean)
    suspend fun allBrands(): List<Brand>
    suspend fun allProducts(): List<Product>


    fun getIsLoggedIn() : Boolean

    fun saveUser(userId: LoginDataResponse?)

    fun getUserId() : String?

    fun removeUser()

    suspend fun resetPassword(recoverUsername: String, confirmPassword: String, otp: String) : ResetDataResponse?
    suspend fun addCustomer(storeName:String, image: File, cityId:String, stateId:String, postalCode: Int, address:String, routeId:String, gpsLocation:String, contactName:String, contactNumber: Int, contactEmail:String, gst:String,) : AddStoreDataResponse?

}