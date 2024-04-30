package com.example.jayasales.model

data class DistributorOrderDataResponse (
    val status: Boolean,
    val order: String,
    val sales_man: String,
    val data: List<DistributorDatum>
)

data class DistributorDatum (
    val order_id: String,
    val distributor_id: String,
    val order_status: String,
    val created_at: String,
    val distributor: Distributor,
    val location: Location,
    val items: Long
)

data class Distributor (
    val name: String,
    val phone: String,
    val email: String
)




data class Location (
    val location_id: String,
    val name: String,
    val pincode: String,
    val state: String
)