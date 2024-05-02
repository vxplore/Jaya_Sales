package com.example.jayasales.model

data class TrackSalesManDataResponse (
    val status: Boolean,
    val data: List<TrackDatum>
)

data class TrackDatum (
    val store_id: String,
    val store_name: String,
    val order_taken: Boolean,
    val visit_time: String
)