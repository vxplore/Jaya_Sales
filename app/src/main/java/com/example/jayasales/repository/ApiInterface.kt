package com.example.jayasales.repository

import android.util.Log
import com.example.jayasales.model.AddStoreDataResponse
import com.example.jayasales.model.AllBrandDataResponse
import com.example.jayasales.model.AllCategory
import com.example.jayasales.model.AllProduct
import com.example.jayasales.model.AllProducts
import com.example.jayasales.model.AttendanceDataResponse
import com.example.jayasales.model.CheckInOutDataResponse
import com.example.jayasales.model.CityDataResponse
import com.example.jayasales.model.DashboardDataResponse
import com.example.jayasales.model.DeleteStoreDataResponse
import com.example.jayasales.model.DistributorOrderDataResponse
import com.example.jayasales.model.DistributorOrderDetailsDataResponse
import com.example.jayasales.model.GetOtpResponse
import com.example.jayasales.model.LoginDataResponse
import com.example.jayasales.model.MarkVisitDataResponse
import com.example.jayasales.model.PartiesDataResponse
import com.example.jayasales.model.PaymentInList
import com.example.jayasales.model.PlaceOrderDataResponse
import com.example.jayasales.model.ReasonDataResponse
import com.example.jayasales.model.ReceivePaymentInList
import com.example.jayasales.model.RemoveResponse
import com.example.jayasales.model.ResetDataResponse
import com.example.jayasales.model.ReturnRequestDataResponse
import com.example.jayasales.model.ReviewCartDataResponse
import com.example.jayasales.model.RouteDataResponse
import com.example.jayasales.model.SearchProductDataResponse
import com.example.jayasales.model.SearchRouteDataResponse
import com.example.jayasales.model.StateDataResponse
import com.example.jayasales.model.StoreDetailsDataResponse
import com.example.jayasales.model.TimeSheetDataResponse
import com.example.jayasales.model.UpdateQuantityDataResponse
import com.example.jayasales.model.UpdateStoreDataResponse
import com.example.jayasales.model.ViewCartDataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.io.File

interface ApiInterface {
    @FormUrlEncoded
    @POST("log-in")
    suspend fun Login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("user_type") user_type: String,
    ): Response<LoginDataResponse>

    @GET("forgot-password")
    suspend fun getOtp(
        @Query("email") email: String,

        ): Response<GetOtpResponse>

    @FormUrlEncoded
    @POST("change-password")
    suspend fun resetPassword(
        @Field("user_id") recoverUsername: String,
        @Field("otp") otp: String,
        @Field("new_password") confirmPassword: String,
    ): Response<ResetDataResponse>

    @FormUrlEncoded
    @POST("change-password")
    suspend fun addCustomer(
        @Field("store-name") storeName: String,
        @Field("image") image: File,
        @Field("city_id") cityId: String,
        @Field("state_id") stateId: String,
        @Field("postal_code") postalCode: Int,
        @Field("address") address: String,
        @Field("route_id") routeId: String,
        @Field("gps_location") gpsLocation: String,
        @Field("contact_name") contactName: String,
        @Field("contact_number") contactNumber: Int,
        @Field("contact_email") contactEmail: String,
        @Field("gst") gst: String,
    ): Response<AddStoreDataResponse>

    @GET("routes-lists")
    suspend fun route(
        @Query("routes-lists") data: String,

        ): Response<RouteDataResponse>

    @GET("sells/attendance")
    suspend fun attendance(
        @Query("user_id") userId: String,

        ): Response<AttendanceDataResponse>

    @GET("sells/timesheet")
    suspend fun timeSheet(
        @Query("user_id") userId: String,

        ): Response<TimeSheetDataResponse>

    @GET("sells/product_brands")
    suspend fun allBrands(
        @Query("sells/product_brands") allBrands: String,

        ): Response<AllBrandDataResponse>

    @GET("sells/product_categories")
    suspend fun allCategories(
        @Query("sells/product_categories") allCategories: String,

        ): Response<AllCategory>

    @GET("routes-search")
    suspend fun searchRoute(
        @Query("query") query: String,

        ): Response<SearchRouteDataResponse>
    @GET("sells/states")
    suspend fun state(
        @Query("states") states: String,
        ): Response<StateDataResponse>
    @GET("sells/cities")
    suspend fun city(
        @Query("state_id") city: String,
        ): Response<CityDataResponse>

    @GET("sells/return_request_reason")
    suspend fun reason(
        @Query("return_request_reason") reason: String,
    ): Response<ReasonDataResponse>

    @FormUrlEncoded
    @POST("sells/store_details")
    suspend fun storeDetails(
        @Field("store_id") storeId: String,
        @Field("user_id") userId: String,
    ): Response<StoreDetailsDataResponse>

    @FormUrlEncoded
    @POST("sells/products")
    suspend fun allProducts(
        @Field("category_id") categoryId: String,
        @Field("brand_id") brandId: String,
        @Field("search_text") searchText: String,
    ): Response<AllProduct>

    @FormUrlEncoded
    @POST("sells/receive_payment")
    suspend fun receivePayment(
        @Field("user_id") userId: String,
        @Field("order_id") orderId: String,
        @Field("store_id") storeId: String,
        @Field("price") price: String,
        @Field("payment_type") paymentType: String,
        @Field("instruction") instruction: String,
    ): Response<ReceivePaymentInList>
    @Multipart
    @POST("sells/add_store")
    suspend fun addStore(
        @Part images: MultipartBody.Part,
        @Part("user_id") userId: RequestBody,
        @Part("store_name") storeName: RequestBody,
        @Part("city_id") cityId: RequestBody,
        @Part("state_id") stateId: RequestBody,
        @Part("postal_code") postalCode: RequestBody,
        @Part("address") address: RequestBody,
        @Part("route_id") routeId: RequestBody,
        @Part("lat") lat: RequestBody,
        @Part("lng") lng: RequestBody,
    ): Response<AddStoreDataResponse>

    @FormUrlEncoded
    @POST("sells/update_store")
    suspend fun updateStore(
        @Field("user_id") userId: String,
        @Field("store_id") storeId: String,
        @Field("owner") owner: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("gst") gst: String,
    ): Response<UpdateStoreDataResponse>
    @Multipart
    @POST("sells/return_request")
    suspend fun returnRequest(
        @Part("user_id") userId: RequestBody,
        @Part("store_id") storeId: RequestBody,
        @Part("product_id") productId: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part("brand_id") brandId: RequestBody,
        @Part("packed_on") packedOn: RequestBody,
        @Part("lot_number") lot: RequestBody,
        @Part("reason_id") reasonId: RequestBody,
        @Part("message") message: RequestBody,
        @Part images: MultipartBody.Part,
    ): Response<ReturnRequestDataResponse>

    @FormUrlEncoded
    @POST("sells/delete_store")
    suspend fun deleteStore(
        @Field("user_id") userId: String,
        @Field("store_id") storeId: String,
    ): Response<DeleteStoreDataResponse>

    @FormUrlEncoded
    @POST("sells/invoice_list")
    suspend fun paymentIn(
        @Field("user_id") userId: String,
        @Field("store_id") storeId: String,
    ): Response<PaymentInList>

    @FormUrlEncoded
    @POST("sells/place_order")
    suspend fun placeOrder(
        @Field("user_id") userId: String,
        @Field("store_id") storeId: String,
    ): Response<PlaceOrderDataResponse>

    @FormUrlEncoded
    @POST("parties-search")
    suspend fun parties(
        @Field("user_id") userId: String,
        @Field("route_id") routeId: String,
        @Field("search_text") searchText: String,

        ): Response<PartiesDataResponse>
    @FormUrlEncoded
    @POST("sells/dashboard")
    suspend fun dashboard(
        @Field("user_id") userId: String,
        ): Response<DashboardDataResponse>

    @FormUrlEncoded
    @POST("sells/mark_visit")
    suspend fun markVisit(
        @Field("user_id") userId: String,
        @Field("store_id") storeId: String,
        @Field("lat") lat: String,
        @Field("lng") lng: String,
        @Field("comment") comment: String,
        ): Response<MarkVisitDataResponse>
    //@FormUrlEncoded
    @POST("sells/add_to_cart")
    suspend fun viewCart(
        @Body request: ViewCartRequest
    ): Response<ViewCartDataResponse>
    data class ViewCartRequest(
        @Field("user_id") val user_id: String,
        @Field("store_id") val store_id: String,
        @Field("products") val products: List<AllProducts>
    )

    @FormUrlEncoded
    @POST("sells/review_cart")
    suspend fun reviewCart(
        @Field("user_id") userId: String,
        @Field("store_id") storeId: String,
    ): Response<ReviewCartDataResponse>

    @FormUrlEncoded
    @POST("sells/remove_cart_product")
    suspend fun remove(
        @Field("user_id") userId: String,
        @Field("store_id") storeId: String,
        @Field("cart_id") cartId: String,
    ): Response<RemoveResponse>
    @FormUrlEncoded
    @POST("sells_manager/distributor_order")
    suspend fun distributorOrder(
        @Field("user_id") userId: String,
    ): Response<DistributorOrderDataResponse>

    @FormUrlEncoded
    @POST("sells_manager/order_details")
    suspend fun distributorOrderDetails(
        @Field("user_id") userId: String,
        @Field("order_id") order_id: String,
    ): Response<DistributorOrderDetailsDataResponse>

    @FormUrlEncoded
    @POST("sells_manager/update_product_quantity")
    suspend fun updateQuantity(
        @Field("user_id") user_id: String,
        @Field("order_id") order_id: String,
        @Field("product_id") product_id: String,
        @Field("quantity") quantity: String,
    ): Response<UpdateQuantityDataResponse>

    @FormUrlEncoded
    @POST("sells_manager/add_new_product")
    suspend fun addProduct(
        @Field("user_id") user_id: String,
        @Field("order_id") order_id: String,
        @Field("product_id") product_id: String,
        @Field("quantity") quantity: String,
    ): Response<UpdateQuantityDataResponse>

    @FormUrlEncoded
    @POST("sells_manager/delete_product")
    suspend fun deleteProduct(
        @Field("user_id") user_id: String,
        @Field("order_id") order_id: String,
        @Field("product_id") product_id: String,
    ): Response<UpdateQuantityDataResponse>

    @FormUrlEncoded
    @POST("sells_manager/confirm_order")
    suspend fun confirmOrder(
        @Field("user_id") user_id: String,
        @Field("order_id") order_id: String,
    ): Response<UpdateQuantityDataResponse>

    @FormUrlEncoded
    @POST("sells_manager/product_list")
    suspend fun searchProduct(
        @Field("search_text") search_text: String,
    ): Response<SearchProductDataResponse>




    @FormUrlEncoded
    @POST("sells/attendance")
    suspend fun checkInOut(
        @Field("user_id") userId: String,
        @Field("lat") lat: String,
        @Field("lng") lng: String,
        @Field("comment") comment: String,
        @Field("status") status: String,
    ): Response<CheckInOutDataResponse>

    @FormUrlEncoded
    @POST("sells/mark_visit")
    suspend fun markVisit1(
        @Field("user_id") userId: String,
        @Field("store_id") storeId: String,
        @Field("lat") lat: String,
        @Field("lng") lng: String,
        @Field("comment") comment: String,

        ): Call<MarkVisitDataResponse>

    object ApiClient {
        private val retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://webhook.site/8d14e770-8cbf-4a14-9805-ae325d7ea90c/")
            .build()

        val apiService: ApiInterface = retrofit.create(ApiInterface::class.java)
    }

    suspend fun apiCall(api: ApiInterface) {
        val call = api.markVisit1("user_id", "store_id", "lat", "lng", "comment")
        call.enqueue(object : Callback<MarkVisitDataResponse> {
            override fun onResponse(
                call: Call<MarkVisitDataResponse>,
                response: Response<MarkVisitDataResponse>
            ) {
                val data = response.body()
                Log.d("cddcv", data.toString())
            }

            override fun onFailure(call: Call<MarkVisitDataResponse>, t: Throwable) {
                Log.d("cddcv", t.message ?: "null")
            }
        })
    }

    suspend fun process() {
        val api = ApiClient.apiService
        apiCall(api)
    }

    @GET("parties-search")
    suspend fun searchParty(
        @Query("query") searchParty: String,

        ): Response<PartiesDataResponse>
}