package com.example.jayasales.repository

import com.example.jayasales.model.GetOtpResponse
import com.example.jayasales.model.LoginDataResponse
import com.example.jayasales.model.PartiesDataResponse
import com.example.jayasales.model.ResetDataResponse
import com.example.jayasales.model.RouteDataResponse
import com.example.jayasales.model.SearchRouteDataResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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
    @GET("routes-lists")
    suspend fun route(
        @Query("routes-lists") data : String,

        ): Response<RouteDataResponse>
    @GET("routes-search")
    suspend fun searchRoute(
        @Query("query") query : String,

        ): Response<SearchRouteDataResponse>
    @GET("parties-list")
    suspend fun parties(
        @Query("parties-list") parties : String,

        ): Response<PartiesDataResponse>

    @GET("parties-search")
    suspend fun searchParty(
        @Query("query") searchParty : String,

        ): Response<PartiesDataResponse>
}