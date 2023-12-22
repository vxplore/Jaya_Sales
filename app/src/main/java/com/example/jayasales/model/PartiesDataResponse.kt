package com.example.jayasales.model

data class PartiesDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<PartiesDatum>
)

data class PartiesDatum (
    val uid: String,
    val store_name: String,
    val route_id: String,
    val gps_location: String,
    val amount: String,
    val status: String,
    val visited_date_time: String? = null
)