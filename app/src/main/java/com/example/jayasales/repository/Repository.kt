package com.example.jayasales.repository

import com.example.jayasales.model.GetOtpResponse
import com.example.jayasales.model.LoginDataResponse
import com.example.jayasales.model.ResetDataResponse
import com.example.jayasales.model.RouteDataResponse
import com.example.jayasales.model.SearchRouteDataResponse

interface Repository {


    suspend fun login(email : String, password : String) : LoginDataResponse?
    suspend fun getOtp(email: String) : GetOtpResponse?
    suspend fun route(data:String) : RouteDataResponse?
    suspend fun searchRoute(query:String) : SearchRouteDataResponse?

    fun setIsLoggedIn(done : Boolean)

    fun getIsLoggedIn() : Boolean

    fun saveUser(userId: LoginDataResponse?)

    fun getUserId() : String?

    fun removeUser()

    suspend fun resetPassword(recoverUsername: String, confirmPassword: String, otp: String) : ResetDataResponse?

}