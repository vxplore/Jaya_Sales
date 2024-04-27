package com.example.jayasales

sealed class OrderType(
    val title: String,
    val type: Type,
    val contentDescription: String
) {
    enum class Type {
        Navigation,
        Action
    }

    object Product : OrderType("Distributor Order", Type.Navigation, "Distributor Order")
    object Services : OrderType("Confirm Orders", Type.Navigation, "Confirm Orders")
}