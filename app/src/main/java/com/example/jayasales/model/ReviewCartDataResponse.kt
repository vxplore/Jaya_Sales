package com.example.jayasales.model

data class ReviewCartDataResponse(
    val status: Boolean,
    val message: String,
    val data: List<Cart>,
    val sub_total: String,
    val cgst: String,
    val gst: String,
    val total: String
)

data class Cart(
    val cart_id: String,
    val quantity: String,
    val product: ProductList,
    val price_for_product: String
)

data class ProductList(
    val id: String,
    val name: String,
    val weight: String,
    val pcs: String,
    val mrp: String,
    val discount: String,
    val sell_price: String
)