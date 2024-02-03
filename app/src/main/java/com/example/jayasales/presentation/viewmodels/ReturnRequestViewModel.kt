package com.example.jayasales.presentation.viewmodels

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.SoftInputMode
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.jayasales.AppContext
import com.example.jayasales.MyDataIds
import com.example.jayasales.Routes
import com.example.jayasales.di.NoConnectivityException
import com.example.jayasales.model.AllBrandDataResponse
import com.example.jayasales.model.AllCategory
import com.example.jayasales.model.PartiesDatum
import com.example.jayasales.model.Product
import com.example.jayasales.model.ReasonDatum
import com.example.jayasales.model.Store
import com.example.jayasales.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReturnRequestViewModel @Inject constructor(
    private val repo: Repository
) : WirelessViewModel() {
    private val product = mutableStateOf("")
    private val brandEndPoint = mutableStateOf("sells/product_brands")
    private val brands = mutableStateListOf<AllBrandDataResponse.Brand>()
    private val categories = mutableStateListOf<AllCategory.Category>()
    private val categoryEndPoint = mutableStateOf("sells/product_categories")
    private val productName = mutableStateListOf<Product>()
    private val lot = mutableStateOf("")
    private val reason = mutableStateListOf<ReasonDatum>()
    private val message = mutableStateOf("")
    private val storeNames = mutableStateListOf<PartiesDatum>()
    private val searchStoreName = mutableStateOf("")
    private val index = mutableStateOf("")
    private val productSearch = mutableStateOf("")
    private val brandId = mutableStateOf("")
    private val categoryId = mutableStateOf("")
    private val userId = mutableStateOf("")
    private val routeId = mutableStateOf("")
    private val productId = mutableStateOf("")
    private val storeId = mutableStateOf("")
    private val reasonId = mutableStateOf("")
    private val reasonEndPoint = mutableStateOf("sells/return_request_reason")
    private val selectedImageUri = mutableStateOf<Uri?>(null)
    private val selectedDate = mutableStateOf("")
    private val returnDialog = mutableStateOf(false)
    private val loadingState = mutableStateOf(false)

    fun setSelectedDate(dateMillis: Long) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = dateFormat.format(Date(dateMillis))
        selectedDate.value = formattedDate
    }

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

            MyDataIds.product -> {
                product.value = arg as String
            }

            MyDataIds.lot -> {
                lot.value = arg as String
            }

            MyDataIds.message -> {
                message.value = arg as String
            }

            MyDataIds.searchStoreName -> {
                searchStoreName.value = arg as String
            }

            MyDataIds.brandId -> {
                index.value = arg as String
                Log.d("dvfv", index.value)
                repo.setReturnBrand(index.value)
                fetchProduct()
            }

            MyDataIds.categoryId -> {
                index.value = arg as String
                Log.d("dvfv", index.value)
                repo.setCategoryId(index.value)
                fetchProduct()
            }
            MyDataIds.productId->{
                index.value = arg as String
                Log.d("dvfv", index.value)
                repo.setReturnProductId(index.value)
            }
            MyDataIds.storeId->{
                index.value = arg as String
                Log.d("dvfv", index.value)
                repo.setReturnStoreId(index.value)
            }
            MyDataIds.reasonId->{
                index.value = arg as String
                Log.d("dvfv", index.value)
                repo.setReasonId(index.value)
            }
            MyDataIds.backHome->{
                navigation {
                    navigate(Routes.home.full)
                }
            }
            MyDataIds.submit->{
                returnRequest()
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }

    init {
        mapData(
            MyDataIds.product to product,
            MyDataIds.brandName to brands,
            MyDataIds.productCategory to categories,
            MyDataIds.lot to lot,
            MyDataIds.reason to reason,
            MyDataIds.message to message,
            MyDataIds.productName to productName,
            MyDataIds.storeNames to storeNames,
            MyDataIds.searchStoreName to searchStoreName,
            MyDataIds.returnDialog to returnDialog,
            MyDataIds.loadingState to loadingState,
        )
        setStatusBarColor(Color(0xFFFFEB56), true)
        setSoftInputMode(SoftInputMode.adjustPan)
        fetchBrands()
        loadAllCategories()
        store()
        reason()
    }

    private fun fetchBrands() {
        val brandEndPoint = brandEndPoint.value
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.allBrands(brandEndPoint)
                withContext(Dispatchers.Main) {
                    brands.clear()
                    //brands.add(AllBrandDataResponse.All)
                    if (response != null) {
                        brands.addAll(response.data)
                        /*for (brand in response.data) {
                            val uid = brand.uid
                            repo.setReturnBrand(uid)
                            Log.d("BrandUID", "Brand UID: $uid")
                        }*/
                    }
                }
            } catch (e: Exception) {
                // Handle exceptions
            }
        }
    }


    private fun loadAllCategories() {
        val categoryEndPoint = categoryEndPoint.value
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.allCategory(categoryEndPoint)
                if (response?.status == true) {
                    categories.clear()
                    categories.addAll(response.data)
                }
            } catch (e: NoConnectivityException) {

            }
        }
    }

    private fun fetchProduct() {
        viewModelScope.launch {
            try {
                brandId.value = repo.getReturnBrand()!!
                categoryId.value = repo.getCategoryId()!!
                productSearch.value = ""
                val response =
                    repo.allProducts(categoryId.value, brandId.value, productSearch.value)
                if (response?.status == true) {
                    productName.clear()
                    val list = response.data
                    productName.addAll(list)
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun store(){
        viewModelScope.launch {
            try {
                userId.value = repo.getUserId()!!
                routeId.value =repo.getRouteId()!!
                searchStoreName.value = ""
                val response = repo.parties(userId.value,routeId.value,searchStoreName.value)
                if (response?.status == true){
                    storeNames.clear()
                    storeNames.addAll(response.data)
                }
            }catch (e:Exception){
            }
        }
    }

    private fun reason(){
        val reasonData = reasonEndPoint.value
        viewModelScope.launch {
            try {
               val response = repo.reason(reasonData)
                if (response?.status == true){
                    reason.clear()
                    reason.addAll(response.data)
                }
            }catch (e:Exception){
            }
        }
    }

    private fun returnRequest() {
        loadingState.value = true
        viewModelScope.launch {
            try {
                userId.value = repo.getUserId()!!
                brandId.value = repo.getReturnBrand()!!
                categoryId.value = repo.getCategoryId()!!
                productId.value = repo.getReturnProductId()!!
                storeId.value = repo.getReturnStoreId()!!
                reasonId.value = repo.getReasonId()!!
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
                        val response = repo.returnRequest(userId.value,storeId.value,productId.value,categoryId.value,brandId.value,selectedDate.value,lot.value,reasonId.value,message.value,imagePart)
                        Log.d("bhfc",selectedDate.value)
                        if (response?.status == true){
                            toast(response.message)
                            returnDialog.value =! returnDialog.value
                        }else{
                            if (response != null) {
                                toast(response.message)
                            }
                        }
                    }
                }
            }catch (e:Exception){
            }
            finally {
                loadingState.value = false
            }
        }
    }
    private fun getImageFileFromUri(uri: Uri): File? {
        val context = AppContext.app.applicationContext
        val contentResolver = context.contentResolver

        // Get file extension from Uri
        val fileExtension = context.contentResolver.getType(uri)?.let { type ->
            MimeTypeMap.getSingleton().getExtensionFromMimeType(type)
        } ?: ""

        Log.d("ImageExtension", "File extension: $fileExtension")

        // Create temporary file with proper extension
        val tempFile = createTempFile("temp_image", ".$fileExtension", context.cacheDir)
        tempFile.deleteOnExit() // Ensures that the temporary file is deleted on JVM exit

        Log.d("ImageExtension", "Temporary file path: ${tempFile.absolutePath}")

        contentResolver.openInputStream(uri)?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return tempFile
    }



}