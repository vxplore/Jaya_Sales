package com.example.jayasales.presentation.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.MyDataIds
import com.example.jayasales.model.PartiesDatum
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PartiesViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val partiesSearch = mutableStateOf("")
    private val allbtn =  mutableStateOf(true)
    private val visitedbtn = mutableStateOf(false)
    private val pendingbtn = mutableStateOf(false)
    private val partiesList = mutableStateListOf<PartiesDatum>()
    private val status = mutableStateOf("")
    private val partiesData = mutableStateOf("parties-list")
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when (id) {
            MyDataIds.partiesSearch->{
                partiesSearch.value = arg as String
                searchParty()
            }
            MyDataIds.allbtn->{
                allbtn.value =! allbtn.value
                visitedbtn.value = false
                pendingbtn.value = false
                //status.value =arg as String
                getPartiesList()
            }
            MyDataIds.visitedbtn->{
                visitedbtn.value =! visitedbtn.value
                allbtn.value = false
                pendingbtn.value = false
              status.value = arg as String
                //getPartiesList()
            }
            MyDataIds.pendingbtn->{
                pendingbtn.value =! pendingbtn.value
                allbtn.value = false
                visitedbtn.value = false
             status.value = arg as String
                pendingPartiesList()
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.partiesSearch to partiesSearch,
            MyDataIds.allbtn to allbtn,
            MyDataIds.pendingbtn to pendingbtn,
            MyDataIds.visitedbtn to visitedbtn,
            MyDataIds.partiesList to partiesList,
        )
    }

   private fun pendingPartiesList() {
        viewModelScope.launch {
            val partiesData = partiesData.value
            try {
                val partiesresponse = repo.parties("Pending")
                if (partiesresponse?.status == false){
                    Log.d("jxdhdcd", "$partiesresponse")
                    partiesList.clear()
                    partiesList.addAll(partiesresponse.data)
                }
            }catch (e:Exception){
                Log.e("jxdhdcd","${e.message}")
            }
        }
    }
    private fun getPartiesList() {
        viewModelScope.launch {
            val partiesData = partiesData.value
            try {
                val response = repo.parties(partiesData)
                if (response?.status == false) {
                    withContext(Dispatchers.Main) {
                        Log.d("jxdhdcd", "$response")
                        partiesList.clear()
                        partiesList.addAll(response.data)
                    }
                }
            }catch (e:Exception){
                Log.e("jxdhdcd","${e.message}")
            }
        }
    }

    private fun searchParty() {
        val queryText = partiesSearch.value
        viewModelScope.launch {
            try {
                val response = repo.searchParty(queryText)
                if (response?.status == true) {
                    partiesList.clear()
                    partiesList.addAll(response.data)
                }
            } catch (e: Exception) {
                Log.e("jxdhdcd", "${e.message}")
            }
        }
    }
}