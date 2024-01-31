package com.example.jayasales.presentation.viewmodels

import android.annotation.SuppressLint
import android.location.Location
import android.net.Uri
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
import com.example.jayasales.AppContext
import com.example.jayasales.MyDataIds
import com.example.jayasales.Routes
import com.example.jayasales.model.CityDatum
import com.example.jayasales.model.Datum
import com.example.jayasales.model.StateDatum
import com.example.jayasales.repository.Repository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class AddNewStoreViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val storeName = mutableStateOf("")
    private val pin = mutableStateOf("")
    private val address = mutableStateOf("")
    private val city = mutableStateListOf<CityDatum>()
    private val state = mutableStateListOf<StateDatum>()
    private val routeList = mutableStateListOf<Datum>()
    private val data = mutableStateOf("sells/states")
    private val routeData = mutableStateOf("routes-lists")
    private val indexRouteId = mutableStateOf("")
    private val stateId = mutableStateOf("")
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val currentLat = mutableStateOf("")
    private val currentLong = mutableStateOf("")
    private val selectedImageUri = mutableStateOf<Uri?>(null)
    private val userId = mutableStateOf("")
    private val cityId = mutableStateOf("")
    private val routeId = mutableStateOf("")
    fun setSelectedImageUri(uri: Uri?) {
        selectedImageUri.value = uri
    }
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

            MyDataIds.storeName -> {
                storeName.value = arg as String
            }

            MyDataIds.pin -> {
                pin.value = arg as String
            }

            MyDataIds.address -> {
                address.value = arg as String
            }

            MyDataIds.contactInformation -> {
                addStore()

            }
            MyDataIds.stateId->{
                indexRouteId.value = arg as String
                Log.d("dsdf",indexRouteId.value)
                repo.setState(indexRouteId.value)
                city()
            }
            MyDataIds.routeIds->{
                indexRouteId.value = arg as String
                Log.d("cfgv",indexRouteId.value)
                repo.setAddRoute(indexRouteId.value)
            }
            MyDataIds.cityId->{
                indexRouteId.value = arg as String
                repo.setCity(indexRouteId.value)
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.storeName to storeName,
            MyDataIds.city to city,
            MyDataIds.state to state,
            MyDataIds.pin to pin,
            MyDataIds.address to address,
            MyDataIds.addRoute to routeList,
            MyDataIds.stateId to indexRouteId,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        state()
        routeDetail()
        tryGetLocation()

    }
    private fun state(){
        val stateData = data.value
        viewModelScope.launch {
            try {
                val response = repo.state(stateData)
                if (response?.status==true){
                    Log.d("jxdhdcd", "$response")
                    state.clear()
                    state.addAll(response.data)
                    Log.d("sds", "${response.data}")
                }
            }catch (e:Exception){
                Log.e("jxdhdcd", "${e.message}")
            }
        }
    }

    private fun city(){
        viewModelScope.launch {
            stateId.value = repo.getState()!!
            Log.d("cdcdc",stateId.value)
            val response = repo.city(stateId.value)
            if (response?.status==true){
                city.clear()
                city.addAll(response.data)
            }
        }
    }
    private fun routeDetail() {
        val data = routeData.value
        viewModelScope.launch {
            try {
                val response = repo.route(data)
                if (response?.status == true) {
                    Log.d("jxdhdcd", "$response")
                    routeList.clear()
                    routeList.addAll(response.data)
                   }
            } catch (e: Exception) {
                Log.e("jxdhdcd", "${e.message}")
            }
        }
    }

    private fun addStore() {
        viewModelScope.launch(Dispatchers.IO) {
            userId.value = repo.getUserId() ?: ""
            cityId.value = repo.getCity() ?: ""
            stateId.value = repo.getState() ?: ""
            routeId.value = repo.getAddRoute() ?: ""

            val imageFile = selectedImageUri.value?.let { uri ->
                // Convert Uri to File
                val context = AppContext.app.applicationContext
                val contentResolver = context.contentResolver
                val inputStream = contentResolver.openInputStream(uri)
                val tempFile = createTempFile("temp_image", null, context.cacheDir)
                tempFile.outputStream().use { outputStream ->
                    inputStream?.copyTo(outputStream)
                }
                tempFile
            }

            imageFile?.let { file ->
                val imageRequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("image", file.name, imageRequestBody)

                val response = repo.addStore(
                    image = imagePart,
                    userId = userId.value,
                    storeName = storeName.value,
                    cityId = cityId.value,
                    stateId = stateId.value,
                    postalCode = pin.value,
                    address = address.value,
                    routeId = routeId.value,
                    lat = currentLat.value,
                    lng = currentLong.value
                )

                if (response?.status == true) {
                    Log.d("hgbj", response.toString())
                    toast(response.message)
                    navigation {
                        navigate(Routes.contactInformation.full)
                    }
                } else {
                    Log.e("addStore", "Failed to add store: ${response?.message}")
                    toast("Failed to add store: ${response?.message}")
                }
            } ?: run {
                Log.e("addStore", "Image file is null.")
                toast("Image file is null.")
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
                    currentLat.value = location.latitude.toString()
                    currentLong.value = location.longitude.toString()
                    Log.d("bgbgg", currentLat.value)
                }
            }
    }
}