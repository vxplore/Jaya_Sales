package com.example.jayasales.model

data class PartiesDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<PartiesDatum>
)

data class PartiesDatum (
    val uid: String,
    val store_name: String,
    val amount: Long,
    val status: String,
    val visited_date_time: String
)