package com.example.jayasales.model

data class SalesManDataResponse (
    val status: Boolean,
    val data: List<SalesManDatum>
)

data class SalesManDatum (
    val id: String,
    val code: String,
    val name: String,
    val phone: String,
    val email: String,
    val today_order: String,
    val status: String,
    val lat: String,
    val lng: String
)