package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.SoftInputMode
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import com.example.jayasales.Routes
import com.example.jayasales.model.Datum
import com.example.jayasales.presentation.screens.Type
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {

    private var passwordVisibility = mutableStateOf(false)
    private val password = mutableStateOf("")
    private val recoverPassword = mutableStateOf("")
    private val confirmPassword = mutableStateOf("")
    private val username = mutableStateOf("")
    private val recoverUsername = mutableStateOf("")
    private val recoverPasswordDialog = mutableStateOf(false)
    private val otpInput = mutableStateOf("")
    private val loading = mutableStateOf(false)
    private val recoverLoading = mutableStateOf(false)
    private val type = mutableStateListOf<Type>()
    private val selectType = mutableStateOf("")
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when (id) {
            MyDataIds.username -> {
                username.value = arg as String
            }

            MyDataIds.password -> {
                password.value = arg as String
            }

            MyDataIds.passwordVisibility -> {
                passwordVisibility.value = !passwordVisibility.value
            }

            MyDataIds.signUpClick -> {
                onClickedSignIn()
            }

            MyDataIds.recoverPasswordClick -> {
                recoverPasswordDialog.value = true
            }

            MyDataIds.otpIntput -> {
                otpInput.value = arg as String
            }

            MyDataIds.resetPassword -> {
                onClickedReset()
            }

            MyDataIds.confirmPassword -> {
                confirmPassword.value = arg as String
            }

            MyDataIds.recoverUserName -> {
                recoverUsername.value = arg as String
            }

            MyDataIds.recoverPassword -> {
                recoverPassword.value = arg as String
            }

            MyDataIds.onDismissDialog -> {
                recoverPasswordDialog.value = false
            }

            MyDataIds.verify -> {
                recoverPass()
            }
            MyDataIds.routeIds->{
                selectType.value = arg as String
                Log.d("xcdhxcd",selectType.value)
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    private fun onClickedSignIn() {

        if (!username.value.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})\$".toRegex())) {
            toast("Enter valid email")
            return
        }
        if (password.value.isEmpty()) {
            toast("Please provide password")
            return
        }

        userLogin()
    }

    private fun onClickedReset() {
        if (recoverUsername.value.isNullOrEmpty()) {
            toast("Please provide email")
            return
        }
        if (!recoverUsername.value.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})\$".toRegex())) {
            toast("Enter valid email")
            return
        }
        if (otpInput.value.isNullOrEmpty()) {
            toast("Please provide email")
            return
        }
        if (recoverPassword.value.isNullOrEmpty()) {
            toast("Please provide password")
            return
        }
        if (confirmPassword.value.isNullOrEmpty()) {
            toast("Please provide Confirm password")
            return
        }
        if (recoverPassword.value.length <= 5 || confirmPassword.value.length <= 5) {
            toast("password & Confirm password should be greater than 5")
            return
        }
        if (recoverPassword.value != confirmPassword.value) {
            toast("password and confirm password not matched")
            return
        }
        resetPassword()
    }

    init {
        setUp()
        type.addAll(listOf(Type("Sales Man","sales_man")))
        type.addAll(listOf(Type("Sales Manager","sales_manager")))
        selectType.value = "Sales Man"
    }

    private fun setUp() {
        controller.resolver.addAll(
            MyDataIds.passwordVisibility to passwordVisibility,
            MyDataIds.username to username,
            MyDataIds.password to password,
            MyDataIds.recoverpasswordDialog to recoverPasswordDialog,
            MyDataIds.otpIntput to otpInput,
            MyDataIds.confirmPassword to confirmPassword,
            MyDataIds.recoverUserName to recoverUsername,
            MyDataIds.recoverPassword to recoverPassword,
            MyDataIds.loading to loading,
            MyDataIds.recoverLoading to recoverLoading,
            MyDataIds.type to type
        )
        setStatusBarColor(Color(0xFFD62B2B), true)
        setSoftInputMode(SoftInputMode.adjustPan)
    }

    private fun userLogin() {
        loading.value = !loading.value
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.login(username.value, password.value,selectType.value)
                if (response != null && response.status) {
                    val responseBody = response.data
                    if (responseBody != null) {
                        Log.d("hujf", response.toString())
                        viewModelScope.launch {
                            repo.setIsLoggedIn(true)
                            repo.setLogUId(response.data.name)
                            repo.setLogEmail(response.data.email)
                            repo.saveUser(response.data.user_id)
                            loading.value = !loading.value
                            repo.setUserType(selectType.value)
                            if (selectType.value=="sales_man") {
                                navigation {
                                    navigate(Routes.home.full)
                                }
                            }else{
                                navigation {
                                    navigate(Routes.managerHome.full)
                                }
                            }
                        }
                    } else {
                        toast("Empty response body")
                    }
                } else {
                    toast("Wrong email or password")
                }
            } catch (e: Exception) {
                Log.d("dbbdk", e.message.toString())
            }finally {
                loading.value = !loading.value
            }
        }
    }

    private fun recoverPass() {
        viewModelScope.launch {
            try {
                val response = repo.getOtp(recoverUsername.value)
                if (response?.status == false) {
                    Log.d("hgbj", response.toString())
                    toast("failed to send OTP")
                } else {
                    toast(response!!.message)
                    toast(response!!.otp)
                    Log.d("hgbj", response.toString())
                }
            } catch (e: Exception) {
                Log.e("hgbj", "Error: ${e.message}")
                toast("Check network connection")
            }
        }
    }

    private fun resetPassword() {
        viewModelScope.launch {
            try {
                val response = repo.resetPassword(
                    recoverUsername.value,
                    otpInput.value,
                    confirmPassword.value,
                )
                Log.d("dbbdk", response.toString())
                if (response?.status == true) {
                    withContext(Dispatchers.Main) {
                        recoverPasswordDialog.value = false
                        username.value = recoverUsername.value
                        toast(response!!.message)
                        clearRecoverInputField()
                    }
                } else {
                    toast("Invalid OTP")
                }
            } catch (e: Exception) {
                Log.e("dbbdk", "Error: ${e.message}")
                toast("Error occurred while resetting password. Please try again.")
            }
        }
    }


    private fun clearRecoverInputField() {
        recoverUsername.value = ""
        recoverPassword.value = ""
        confirmPassword.value = ""
        otpInput.value = ""
    }
}