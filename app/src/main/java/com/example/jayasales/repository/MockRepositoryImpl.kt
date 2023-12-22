package com.example.jayasales.repository

import com.example.jayasales.model.GetOtpResponse
import com.example.jayasales.model.LoginDataResponse
import com.example.jayasales.model.PartiesDataResponse
import com.example.jayasales.model.ResetDataResponse
import com.example.jayasales.model.RouteDataResponse
import com.example.jayasales.model.SearchRouteDataResponse
import com.example.jayasales.repository.preference.PrefRepository
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
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun searchRoute(query: String): SearchRouteDataResponse? {
        val response = apiHelper.searchRoute(query)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun parties(parties: String): PartiesDataResponse? {
        val response = apiHelper.parties(parties)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun searchParty(searchParty: String): PartiesDataResponse? {
        val response = apiHelper.searchParty(searchParty)
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


    override fun setIsLoggedIn(done: Boolean) {
        myPref.setIsLoggedIn(done)
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

    override fun removeUser() {
        myPref.deleteUserId()
    }

}