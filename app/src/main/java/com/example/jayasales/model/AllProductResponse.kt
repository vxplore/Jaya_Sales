package com.example.jayasales.model

data class AllProductResponse (
    val user_id: String,
    val store_id: String,
    val products: List<AllProducts>
)

data class AllProducts (
    val product_id: String,
    val quantity: String
)
