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
import com.example.jayasales.model.PartiesDatum
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

enum class PartiesTab {
    All,
    Visited,
    Pending
}

@HiltViewModel
class PartiesViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val partiesSearch = mutableStateOf("")
    private val userId  = mutableStateOf("")
    private val routeId  = mutableStateOf("")
    private val selectedTab = mutableStateOf(PartiesTab.All)
    private val effectivePartiesList = mutableStateListOf<PartiesDatum>()
    private val allPartiesList = mutableStateListOf<PartiesDatum>()
    //private val partiesData = mutableStateOf("parties-search")
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

            MyDataIds.partiesSearch -> {
                partiesSearch.value = arg as String
                filter()
            }

            MyDataIds.allbtn -> {
                selectedTab.value = PartiesTab.All
                filter()
            }

            MyDataIds.visitedbtn -> {
                selectedTab.value = PartiesTab.Visited
                filter()
            }

            MyDataIds.pendingbtn -> {
                selectedTab.value = PartiesTab.Pending
                filter()
            }

            MyDataIds.storeDetails -> {
                val storedtls = arg as PartiesDatum
                Log.d("dfcvf","$storedtls")
                viewModelScope.launch {
                    repo.setUId(storedtls.uid)
                }
                navigation {
                    navigate(Routes.storeDetails.full)
                }
            }

            MyDataIds.addStore -> {
                navigation {
                    navigate(Routes.addNewStore.full)
                }
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.partiesSearch to partiesSearch,
            MyDataIds.SelectedTab to selectedTab,
            MyDataIds.partiesList to effectivePartiesList,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        pendingPartiesList()
    }

    private fun pendingPartiesList() {
        viewModelScope.launch {
            //val partiesData = partiesData.value
            userId.value = "USER_78u88isit6yhadolutedd"
            routeId.value = "ROUTE_003"
            val searchText = ""
            try {
                val response = repo.parties(userId.value,routeId.value,searchText)
                if (response?.status == true) {
                     Log.d("gbhg", "$response")
                    val list = response.data
                    mainScope {
                       allPartiesList.clear()
                        allPartiesList.addAll(list)
                        filter()
                    }
                }
            } catch (e: Exception) {
                Log.e("gbhg", "${e.message}")
            }
        }
    }

    private fun filter() {
        val query = partiesSearch.value
        val tabString = when (selectedTab.value) {
            PartiesTab.All -> ""
            PartiesTab.Visited -> "Visited"
            PartiesTab.Pending -> "Pending"
        }
        val filteredList = allPartiesList.filter {
            itemFilter(it, query, tabString)
        }
        effectivePartiesList.clear()
        effectivePartiesList.addAll(filteredList)
    }

    private fun itemFilter(
        item: PartiesDatum,
        query: String,
        tabString: String
    ): Boolean {
        val queryAllowance = query.isEmpty() || item.store_name.contains(query, true)
        if (!queryAllowance) {
            return false
        }
        return tabString.isEmpty() || tabString.equals(item.status, true)
    }
}