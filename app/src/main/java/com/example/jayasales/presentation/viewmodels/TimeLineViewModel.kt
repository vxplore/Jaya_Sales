package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import com.example.jayasales.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TimeLineViewModel @Inject constructor(
):WirelessViewModel(){
    private val today = mutableStateOf("")
    private val mYear = mutableStateOf(0)
    private val mMonth = mutableStateOf(0)
    private val mDay = mutableStateOf(0)
    private val mCalendar= Calendar.getInstance()
    private val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT)
    private val currentDate = mutableStateOf(16516161616)
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when(id){
            MyDataIds.back->{
                popBackStack()
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }
    init {
        mapData(
            MyDataIds.mYear to mYear,
            MyDataIds.mMonth to mMonth,
            MyDataIds.mDay to mDay,
            MyDataIds.Currentdate to currentDate,
            MyDataIds.todayDate to today,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        viewModelScope.launch {
            mYear.value= mCalendar.get(Calendar.YEAR)
            mMonth.value = mCalendar.get(Calendar.MONTH)
            mDay.value = mCalendar.get(Calendar.DAY_OF_MONTH)
            currentDate.value=mCalendar.timeInMillis
            today.value = formatter.format(Date())

        }
    }
}