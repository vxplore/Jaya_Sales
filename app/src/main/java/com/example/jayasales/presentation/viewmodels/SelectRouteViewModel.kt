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
import com.example.jayasales.model.Datum
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectRouteViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val routeSearch = mutableStateOf("")
    private val routeList = mutableStateListOf<Datum>()
    private val data = mutableStateOf("routes-lists")
    private val isSearchResultEmpty = mutableStateOf(false)

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

            MyDataIds.routeSearch -> {
                routeSearch.value = arg as String
                searchRoute()
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.routeSearch to routeSearch,
            MyDataIds.routeList to routeList,
            MyDataIds.isSearchResultEmpty to isSearchResultEmpty
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        routeDetail()
    }

    private fun routeDetail() {
        val data = data.value
        viewModelScope.launch {
            try {
                val response = repo.route(data)
                if (response?.status == true) {
                    Log.d("jxdhdcd", "$response")
                    routeList.clear()
                    routeList.addAll(response.data)
                    isSearchResultEmpty.value = routeList.isEmpty()
                    //filter()
                }
            } catch (e: Exception) {
                Log.e("jxdhdcd", "${e.message}")
            }
        }
    }

    private fun searchRoute() {
        val queryText = routeSearch.value
        viewModelScope.launch {
            try {
                val response = repo.searchRoute(queryText)
                if (response?.status == true) {
                    Log.d("hhn", "$response")
                    routeList.clear()
                    routeList.addAll(response.data)
                } else {
                    // Clear routeList if the response is not successful or data is empty
                    routeList.clear()
                }
            } catch (e: Exception) {
                Log.e("hhn", "${e.message}")
                // Clear routeList in case of an exception
                routeList.clear()
            }
        }
    }
}