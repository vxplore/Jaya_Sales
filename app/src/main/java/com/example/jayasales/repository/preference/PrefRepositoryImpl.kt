package com.example.jayasales.repository.preference

import android.content.Context
import android.content.SharedPreferences
import com.example.jayasales.model.LoginDataResponse
import com.example.jayasales.model.PartiesDataResponse
import javax.inject.Inject


class PrefRepositoryImpl @Inject constructor(
    context : Context
): PrefRepository {

    private val isLoggedInKey="isLoggedInKey"
    private val userIdKey="userIdKey"
    private val uIdKey="uIdKey"
    private val logUIdKey="logUIdKey"

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

    override fun setUserId(userId: LoginDataResponse?) {
        myPref.edit().putString(userIdKey, userId.toString()).apply()
    }

    override fun getUserId(): String? {
        return myPref.getString(userIdKey,"")
    }

    override fun setUId(uId: String?) {
      myPref.edit().putString(uIdKey,uId.toString()).apply()
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

    override fun deleteUserId() {
        myPref.edit().remove(userIdKey).apply()
    }

}