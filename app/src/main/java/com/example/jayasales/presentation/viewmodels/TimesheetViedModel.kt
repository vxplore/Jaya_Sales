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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimesheetViedModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val showTimeSheet = mutableStateListOf<TimeSheet>()
    private val userId = mutableStateOf("")
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
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.showTimeSheet to showTimeSheet,
        )
        timeSheet()
        setStatusBarColor(Color(0xFFFFEB56), true)
    }

    private fun timeSheet() {
        viewModelScope.launch {
            userId.value = "USER_78u88isit6yhadolutedd"
            val response = repo.timeSheet(userId.value)
            Log.d("jxdhdcd", "$response")
            if (response?.status == true) {
                showTimeSheet.clear()
                showTimeSheet.addAll(response.data)
            }
        }
    }
}