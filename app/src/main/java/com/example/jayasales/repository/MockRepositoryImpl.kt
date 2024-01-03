package com.example.jayasales.repository

import android.annotation.SuppressLint
import com.example.jayasales.model.AddStoreDataResponse
import com.example.jayasales.model.Brand
import com.example.jayasales.model.BrandDetail
import com.example.jayasales.model.Category
import com.example.jayasales.model.CategoryDetail
import com.example.jayasales.model.GetOtpResponse
import com.example.jayasales.model.LoginDataResponse
import com.example.jayasales.model.PartiesDataResponse
import com.example.jayasales.model.Product
import com.example.jayasales.model.ResetDataResponse
import com.example.jayasales.model.RouteDataResponse
import com.example.jayasales.model.SearchRouteDataResponse
import com.example.jayasales.repository.preference.PrefRepository
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

    override suspend fun allBrands(): List<Brand> {
        return listOf(
            Brand(
                "1",
                "JAYA",
                listOf(
                    Category("1", "Sweet"),
                    Category("2", "Semi-Sweet")
                )
            ),
            Brand(
                "2",
                "HERO",
                listOf(
                    Category("3", "Cracker"),
                    Category("4", "Namkeen")
                )
            ),
            Brand(
                "3",
                "JAGADHATRI",
                listOf(
                    Category("5", "Cream"),
                )
            ),
        )
    }

    override suspend fun allProducts(): List<Product> {
        return listOf(
            Product(
                1,
                "Big Boss",
                brandDetails = BrandDetail("1", "JAYA"),
                categoryDetails = CategoryDetail("1", "Sweet"),
                "Biscuit",
                70f,
                "50/PAC",
                0,
                50
            ),
            Product(
                2,
                "Chatpati",
                brandDetails = BrandDetail("2", "HERO"),
                categoryDetails = CategoryDetail("4", "Namkeen"),
                "Biscuit",
                70f,
                "50/PAC",
                0,
                50
            ),
            Product(
                3,
                "Oreo",
                brandDetails = BrandDetail("3", "JAGADHATRI"),
                categoryDetails = CategoryDetail("5", "Cream"),
                "Biscuit",
                70f,
                "50/PAC",
                0,
                50
            )
        )
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

    override suspend fun searchRoute(query: String): SearchRouteDataResponse? {
        val response = apiHelper.searchRoute(query)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun parties(parties: String): PartiesDataResponse? {
        val response = apiHelper.parties(parties)
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
      val response = apiHelper.addCustomer(storeName, image, cityId, stateId, postalCode, address, routeId, gpsLocation, contactName, contactNumber, contactEmail, gst)
        return if (response.isSuccessful){
            response.body()
        }else{
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