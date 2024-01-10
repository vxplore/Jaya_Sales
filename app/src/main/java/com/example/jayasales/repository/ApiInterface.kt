package com.example.jayasales.repository

import com.example.jayasales.model.AddStoreDataResponse
import com.example.jayasales.model.GetOtpResponse
import com.example.jayasales.model.LoginDataResponse
import com.example.jayasales.model.MarkVisitDataResponse
import com.example.jayasales.model.PartiesDataResponse
import com.example.jayasales.model.ResetDataResponse
import com.example.jayasales.model.RouteDataResponse
import com.example.jayasales.model.SearchRouteDataResponse
import com.example.jayasales.model.StoreDetailsDataResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.File

interface ApiInterface {
    @FormUrlEncoded
    @POST("log-in")
    suspend fun Login(
        @Field("email") email : String,
        @Field("password") password : String,
    ): Response<LoginDataResponse>

    @GET("forgot-password")
    suspend fun getOtp(
        @Query("email") email : String,

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
        @Query("routes-lists") data : String,

        ): Response<RouteDataResponse>
    @GET("routes-search")
    suspend fun searchRoute(
        @Query("query") query : String,

        ): Response<SearchRouteDataResponse>
    @FormUrlEncoded
    @POST("sells/store_details")
    suspend fun storeDetails(
        @Field("store_id") storeId : String,
        @Field("user_id") userId: String,
        ): Response<StoreDetailsDataResponse>

    @FormUrlEncoded
    @POST("parties-search")
    suspend fun parties(
        @Field("user_id") userId : String,
        @Field("route_id") routeId: String,
        @Field("search_text") searchText : String,

        ): Response<PartiesDataResponse>
    @FormUrlEncoded
    @POST("sells/mark_visit")
    suspend fun markVisit(
        @Field("user_id") userId: String,
        @Field("store_id") storeId: String,
        @Field("lat") lat: String,
        @Field("lng") lng: String,
        @Field("comment") comment: String,

        ): Response<MarkVisitDataResponse>

    @GET("parties-search")
    suspend fun searchParty(
        @Query("query") searchParty : String,

        ): Response<PartiesDataResponse>
}