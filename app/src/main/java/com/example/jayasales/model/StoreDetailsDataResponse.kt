package com.example.jayasales.model


data class StoreDetailsDataResponse (
    val status: Boolean,
    val store: Store,
    val transactions: List<Any?>
)

data class Store (
    val store_name: String,
    val phone: String,
    val lat: String,
    val lng: String,
    val address: String,
    val due_amount: Long
)