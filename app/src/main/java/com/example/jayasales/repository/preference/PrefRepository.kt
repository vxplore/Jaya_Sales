package com.example.jayasales.repository.preference

import com.example.jayasales.model.LoginDataResponse

interface PrefRepository {

    fun setIsLoggedIn(isLoggedIn: Boolean)

    fun getIsLoggedIn(): Boolean

    fun setUserId(userId: LoginDataResponse?)

    fun getUserId(): String?

    fun deleteUserId()
}