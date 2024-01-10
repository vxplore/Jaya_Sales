package com.example.jayasales.model


data class StoreDetailsDataResponse (
    val status: Boolean,
    val store: Store,
    val order_list: List<OrderList>
)

data class OrderList (
    val id: String,
    val total: String,
    val due_amount: String,
    val status: String,
    val store_name: String,
    val date: String,
    val time: String
)

data class Store (
    val store_name: String,
    val phone: String,
    val lat: String,
    val lng: String,
    val address: String,
    val due_amount: Long
)
