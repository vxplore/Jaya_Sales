package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import com.example.jayasales.Routes
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactInformationViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val name = mutableStateOf("")
    private val phnNo = mutableStateOf("")
    private val email = mutableStateOf("")
    private val gst = mutableStateOf("")
    private val storeDialog = mutableStateOf(false)
    private val userId = mutableStateOf("")
    private val storeId = mutableStateOf("")
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when (id) {
            MyDataIds.back -> {
                deleteStore()
                popBackStack()
            }

            MyDataIds.c_name -> {
                name.value = arg as String
            }

            MyDataIds.phnNo -> {
                phnNo.value = arg as String
            }

            MyDataIds.email -> {
                email.value = arg as String
            }

            MyDataIds.gst -> {
                gst.value = arg as String
            }

            MyDataIds.addNow -> {
               updateStore()
            }

            MyDataIds.backNow -> {
               deleteStore()
            }

            MyDataIds.addNewStore -> {
                navigation {
                    navigate(Routes.parties.full)
                }
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.c_name to name,
            MyDataIds.phnNo to phnNo,
            MyDataIds.email to email,
            MyDataIds.gst to gst,
            MyDataIds.storeDialog to storeDialog,
        )

        setStatusBarColor(Color(0xFFFFEB56), true)
    }

    private fun updateStore() {
        viewModelScope.launch {
            userId.value = repo.getUserId()!!
            storeId.value = repo.getAddStoreId()!!
            val response = repo.updateStore(userId.value,storeId.value,name.value,phnNo.value,email.value,gst.value)
            if (response?.status==true){
                Log.d("dcfdc","$response")
                toast(response.message)
                storeDialog.value = !storeDialog.value
            }
        }
    }
    private fun deleteStore(){
        viewModelScope.launch {
            userId.value = repo.getUserId()!!
            storeId.value = repo.getAddStoreId()!!
            val response = repo.deleteStore(userId.value,storeId.value)
            if (response?.status == true){
                //toast(response.message)
                navigation {
                    navigate(Routes.addNewStore.full)
                }
            }
        }
    }
}