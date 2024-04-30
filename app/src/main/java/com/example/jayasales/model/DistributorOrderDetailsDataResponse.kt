package com.example.jayasales.model

data class DistributorOrderDetailsDataResponse (
    val status: Boolean,
    val message: String,
    val order: List<Order>,
    val products: List<DistributorDetailsProduct>
)

data class Order (
    val order_id: String,
    val distributor_id: String,
    val order_status: String,
    val created_at: String,
    val distributor: DistributorDetails,
    val location: DistributorDetailsLocation,
    val items: Long
)

data class DistributorDetails (
    val name: String,
    val phone: String,
    val email: String
)

data class DistributorDetailsLocation (
    val location_id: String,
    val name: String,
    val pincode: String,
    val state: String
)

data class DistributorDetailsProduct (
    val product_id: String,
    val code: String,
    val name: String,
    val weight: String,
    val pcs: String,
    val quantity: String
)