package com.example.jayasales.presentation.viewmodels

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.AppContext
import com.example.jayasales.MyDataIds
import com.example.jayasales.Routes
import com.example.jayasales.model.Attendance
import com.example.jayasales.repository.Repository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarkAttendanceViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val attencomments = mutableStateOf("")
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val currentLat = mutableStateOf("")
    private val currentLong = mutableStateOf("")
    private val currentInTime = mutableStateOf("")
    private val currentOutTime = mutableStateOf("")
    private val userId = mutableStateOf("")
    private val status = mutableStateOf("")
    private val data = mutableStateOf("sells/attendance")
    private val attendanceList = mutableStateListOf<Attendance>()

    val currentInTimeState: State<String> get() = currentInTime
    val currentOutTimeState: State<String> get() = currentOutTime
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
                navigation {
                    navigate(Routes.home.full)
                }
            }

            MyDataIds.attencomments -> {
                attencomments.value = arg as String
            }

            MyDataIds.timeSheet -> {
                navigation {
                    navigate(Routes.timeSheet.full)
                }
            }

            MyDataIds.status -> {
                status.value = arg as String
                Log.d("ghgh", status.value)
            }

            MyDataIds.btnClick -> {
                markAttendance()
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.attencomments to attencomments,
            MyDataIds.status to status,
            MyDataIds.attendanceList to attendanceList,
            MyDataIds.currentInTimeState to currentInTimeState,
            MyDataIds.currentOutTimeState to currentOutTimeState,
        )
        tryGetLocation()
        attendance()
        setStatusBarColor(Color(0xFFFFEB56), true)
    }

    private fun markAttendance() {
        viewModelScope.launch(Dispatchers.IO) {
            userId.value = "USER_78u88isit6yhadolutedd"
            try {
                val response = repo.checkInOut(
                    userId.value,
                    currentLat.value,
                    currentLong.value,
                    attencomments.value,
                    status.value
                )
                if (response?.status == true) {
                    Log.d("hgbj", response.toString())
                    toast(response.message)
                } else {
                    Log.d("hgbj", response.toString())
                    if (response != null) {
                        toast(response.message)
                    }
                }
            } catch (e: Exception) {
                Log.e("hgbj", "Error: ${e.message}")
                toast("Check network connection")
            }
        }
    }

    private fun attendance() {
        val data = data.value
        viewModelScope.launch {
            try {
                val response = repo.attendance(data)
                if (response?.status == true) {
                    Log.d("dcdcvdf", "$response")
                    val list = response.data
                    mainScope {
                        attendanceList.clear()
                        attendanceList.addAll(listOf(list))
                        Log.d("fvg", list.check_in)
                        currentInTime.value = list.check_in
                        currentOutTime.value = list.check_out
                        Log.d("fgvr", currentInTime.value)
                    }

                }
            } catch (e: Exception) {
                Log.e("dcdcvdf", "${e.message}")
            }
        }
    }

    private fun tryGetLocation() {
        viewModelScope.launch {
            val p = listOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
            )

            val granted = p.checkPermission()
            if (granted?.allPermissionsGranted == true) {
                getLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(AppContext.app)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                toast("${location?.latitude},${location?.longitude}")
                Log.d("kibjbk", "latitude ${location?.latitude}, longitude ${location?.longitude}")
                if (location != null) {
                    currentLat.value = location?.latitude.toString()!!
                    currentLong.value = location?.longitude.toString()!!
                    Log.d("bgbgg", "${currentLat.value}")
                }
            }
    }
}