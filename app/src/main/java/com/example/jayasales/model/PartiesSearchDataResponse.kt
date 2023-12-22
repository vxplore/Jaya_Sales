package com.example.jayasales.model

data class PartiesSearchDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<SearchParty>
)

data class SearchParty (
    val uid: String,
    val store_name: String,
    val route_id: String,
    val gps_location: String,
    val amount: String
)