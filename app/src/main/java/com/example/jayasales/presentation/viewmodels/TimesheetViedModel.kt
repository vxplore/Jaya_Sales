package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import com.example.jayasales.model.TimeSheet
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TimesheetViedModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val showTimeSheet = mutableStateListOf<TimeSheet>()
    private val userId = mutableStateOf("")
    private val timeSheetLoadingState = mutableStateOf(false)
    private val lostInternet = mutableStateOf(false)

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
                popBackStack()
            }
            MyDataIds.tryagain->{
                lostInternet.value = false
                timeSheet()
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.showTimeSheet to showTimeSheet,
            MyDataIds.timeSheetLoadingState to timeSheetLoadingState,
            MyDataIds.lostInternet to lostInternet
        )
        timeSheet()
        setStatusBarColor(Color(0xFFFFEB56), true)
    }

    private suspend fun handleNoConnectivity() {
        withContext(Dispatchers.Main) {
            lostInternet.value = true
        }
    }

    private fun timeSheet() {
        timeSheetLoadingState.value = true
        viewModelScope.launch {
            try {
                userId.value = "USER_78u88isit6yhadolutedd"
                val response = repo.timeSheet(userId.value)
                Log.d("jxdhdcd", "$response")
                if (response?.status == true) {
                    showTimeSheet.clear()
                    showTimeSheet.addAll(response.data)
                }
            } catch (e: Exception) {
                handleNoConnectivity()
                Log.e("bhcdxgh", "${e.message}")
            } finally {
                timeSheetLoadingState.value = false
            }
        }
    }
}