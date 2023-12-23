package com.example.jayasales.model

data class StoreDataResponse (
    val order_id: String,
    val order_status: String,
    val store_name: String,
    val date: String,
    val time: String,
    val amount: String,
    val transaction_status: String,
    val payment_mode: String,
)