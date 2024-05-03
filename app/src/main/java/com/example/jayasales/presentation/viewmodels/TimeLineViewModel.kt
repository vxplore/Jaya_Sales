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
import com.example.jayasales.Routes
import com.example.jayasales.model.SalesManDatum
import com.example.jayasales.model.TrackDatum
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
@HiltViewModel
class TimeLineViewModel @Inject constructor(
    private val repo: Repository
):WirelessViewModel(){
    private val today = mutableStateOf("")
    private val mYear = mutableStateOf(0)
    private val mMonth = mutableStateOf(0)
    private val mDay = mutableStateOf(0)
    private val mCalendar= Calendar.getInstance()
    val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.ROOT)
    private val currentDate = mutableStateOf(16516161616)
    val selectedDate = mutableStateOf<Date?>(null)
    private val userId = mutableStateOf("")
    private val salesmanId = mutableStateOf("")
    private val tracker = mutableStateListOf<TrackDatum>()
    private val loadingState = mutableStateOf(false)
    private val nameState = mutableStateOf("")


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

    private fun updateTodayValue() {
        today.value = formatter.format(Date())
    }

    init {
        mapData(
            MyDataIds.mYear to mYear,
            MyDataIds.mMonth to mMonth,
            MyDataIds.mDay to mDay,
            MyDataIds.Currentdate to currentDate,
            MyDataIds.todayDate to today,
            MyDataIds.tracker to tracker,
            MyDataIds.loadingState to loadingState,
            MyDataIds.nameState to nameState,
        )

        timeline()
        setStatusBarColor(Color(0xFFFFEB56), true)
        viewModelScope.launch {
            mYear.value= mCalendar.get(Calendar.YEAR)
            mMonth.value = mCalendar.get(Calendar.MONTH)
            mDay.value = mCalendar.get(Calendar.DAY_OF_MONTH)
            currentDate.value=mCalendar.timeInMillis
            nameState.value = repo.getCallNo()!!
            Log.d("ckidcd", formatter.format(Date(currentDate.value)))
            updateTodayValue() // Call the function to update today.value
            Log.d("ckidcd", today.value)
        }
    }
    fun updateSelectedDate(newDate: Date) {
        selectedDate.value = newDate
        today.value = formatter.format(newDate)
        timeline()
        Log.d("dfvdxfvg",today.value.toString())
    }

    private fun timeline(){
        loadingState.value = true
        userId.value = repo.getUserId()!!
        salesmanId.value = repo.getSalesmenId()!!
        Log.d("uhdk", userId.value)
        viewModelScope.launch {
            try {
                val response = repo.trackSalesman(userId.value,salesmanId.value, today.value)
                if (response?.status == true){
                    tracker.clear()
                    tracker.addAll(response.data)
                }
            }catch (e: Exception) {
                Log.e("ygdch", e.message.toString())
            } finally {
                loadingState.value = false
            }
        }
    }
}
