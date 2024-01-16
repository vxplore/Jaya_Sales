package com.example.jayasales.model
data class AllProduct (
    val status: Boolean,
    val message: String,
    val data: List<Product>
)

data class Product (
    val uid: String,
    val name: String,
    val weight: String,
    val pcs: String,
    val mrp: String,
    val discount: String,
    val sell_price: String,
    val image: String
)