package com.example.jayasales.model

data class SearchProductDataResponse (
    val status: Boolean,
    val message: String,
    val data: SearchData
)

data class SearchData (
    val product_id: String,
    val code: String,
    val name: String,
    val weight: String,
    val pcs: String,
    val kgs: String,
    val mrp: String,
    val sell_price: String,
    val category: String,
    val brand: String
)
