package com.example.jayasales.presentation.viewmodels

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
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
import com.example.jayasales.di.NoConnectivityException
import com.example.jayasales.model.CityDatum
import com.example.jayasales.model.Datum
import com.example.jayasales.model.StateDatum
import com.example.jayasales.repository.Repository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
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
    private val loadingState = mutableStateOf(false)
    private val lostInternet = mutableStateOf(false)
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
            MyDataIds.stateId -> {
                if (arg is String) {
                    indexRouteId.value = arg
                    Log.d("dsdf", indexRouteId.value)
                    repo.setState(indexRouteId.value)
                    city()
                } else {
                    Log.e("dsdf", "arg is not a String")
                }
            }
            MyDataIds.tryagain -> {
                lostInternet.value = false
                //state()
                //city()
                addStore()
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
            MyDataIds.loadingState to loadingState,
            MyDataIds.lostInternet to lostInternet,
            // MyDataIds.stateId to indexRouteId,
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
            }catch (e: NoConnectivityException) {
                handleNoConnectivity()
                Log.d("fgffg", "${e.message}")
            }
        }
    }

    private fun city(){
        viewModelScope.launch {
            try {
                stateId.value = repo.getState()!!
                Log.d("cdcdc", stateId.value)
                val response = repo.city(stateId.value)
                if (response?.status == true) {
                    city.clear()
                    city.addAll(response.data)
                }
            }catch (e: NoConnectivityException) {
                handleNoConnectivity()
                Log.d("fgffg", "${e.message}")
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
            }catch (e: NoConnectivityException) {
                handleNoConnectivity()
                Log.d("fgffg", "${e.message}")
            }
        }
    }

    private suspend fun handleNoConnectivity() {
        withContext(Dispatchers.Main) {
            lostInternet.value = true
        }
    }

    private fun addStore() {
        loadingState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userId.value = repo.getUserId()!!
                cityId.value = repo.getCity()!!
                stateId.value = repo.getState()!!
                routeId.value = repo.getAddRoute()!!
                val imageUri = selectedImageUri.value
                if (imageUri != null) {
                    val imageFile = getImageFileFromUri(imageUri)
                    Log.d("hbcf", "$imageFile")
                    if (imageFile != null) {
                        val imageRequestBody =
                            imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                        val imagePart = MultipartBody.Part.createFormData(
                            "images[]",
                            imageFile.name,
                            imageRequestBody
                        )
                        Log.d("ghnhg", "${imageFile.name}")
                        val response = repo.addStore(
                            images = imagePart,
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
                            Log.d("addStore", "Store added successfully.")
                            toast(response.message)
                            val storeId = response?.store_id ?: ""
                            repo.setAddStoreId(storeId)
                            Log.d(" cvfcv ", storeId)

                            navigation {
                                navigate(Routes.contactInformation.full)
                            }

                            // Log the path of the uploaded image
                            Log.d(
                                "ImageFilePath",
                                "Image uploaded successfully, Path: ${imageFile.absolutePath}"
                            )
                        } else {
                            val errorMessage = response?.message ?: "Unknown error occurred."
                            Log.e("addStore", "Failed to add store: $errorMessage")
                            toast("Failed to add store: $errorMessage")
                        }
                    } else {
                        Log.e("addStore", "Failed to get image file from URI.")
                        toast("Failed to get image file from URI.")
                    }
                } else {
                    Log.e("addStore", "Selected image URI is null.")
                    toast("Selected image URI is null.")
                }
            }catch (e: NoConnectivityException) {
                handleNoConnectivity()
                Log.d("fgffg", "${e.message}")
            }
            finally {
                loadingState.value = false
            }
        }
    }



    private fun getImageFileFromUri(uri: Uri): File? {
        val context = AppContext.app.applicationContext
        val contentResolver = context.contentResolver
        val tempFile = createTempFile("temp_image", ".jpg", context.cacheDir)
        Log.d("hbcf","$tempFile")
        tempFile.deleteOnExit() // Ensures that the temporary file is deleted on JVM exit
        Log.d("hbcf","$tempFile")
        contentResolver.openInputStream(uri)?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return tempFile
    }


    @SuppressLint("Range")
    private fun getDisplayName(contentResolver: ContentResolver, uri: Uri): String {
        var displayName: String? = null
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                displayName = it.getString(it.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME))
            }
        }
        return displayName ?: "temp_image" // fallback if display name is null
    }

    private fun getFileExtension(uri: Uri): String? {
        val mimeTypeMap = MimeTypeMap.getSingleton()
        val extension = mimeTypeMap.getExtensionFromMimeType(AppContext.app.contentResolver.getType(uri))
        return extension ?: uri.path?.substringAfterLast('.', "")
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

    /* private fun grantCameraPermission() {
         viewModelScope.launch {
             val p = listOf(
                 android.Manifest.permission.CAMERA,
                 android.Manifest.permission.READ_EXTERNAL_STORAGE
             )
             val granted = p.checkPermission()
             if (granted?.allPermissionsGranted == true) {
                 profilePicture.value = takePicturePreview()
                 Log.d("sdcndswjo", "${profilePicture.value}")
             } else {
                 val result = listOf(
                     android.Manifest.permission.CAMERA,
                     android.Manifest.permission.READ_EXTERNAL_STORAGE
                 ).requestPermission()
                 if (result.multiPermissionState?.allPermissionsGranted == true) {
                     profilePicture.value = takePicturePreview()
                     Log.d("sdcndswjo", "${profilePicture.value}")
                 } else {
                     toast("Please grant permission for location from settings")
                 }
             }

             viewModelScope.launch {
                 profile_image.value = bitmapToFile( profilePicture.value!!).absoluteFile
                 Log.d("swdwed", "${profile_image.value}")
                 //repo.sendImage(profile_image.value!!)

             }
         }

     }*/

    fun bitmapToFile( bitmap: Bitmap): File {
        val context = AppContext.app.applicationContext
        val f = File( context.filesDir,System.nanoTime().toString() /*+ "_" + "profile_image.jpg"*/)
        f.createNewFile()
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos)
        val bitmapdata = bos.toByteArray()
        val fos = FileOutputStream(f)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
        Log.d("asas","${f.exists()}")
        return f
    }
}