package com.example.jayasales.model

data class PaymentInList (
    val status: Boolean,
    val data: List<PaymentIn>
)

data class PaymentIn (
    val id: String,
    val total: String,
    val due_amount: String,
    val status: String,
    val store_name: String,
    val date: String,
    val time: String,
    val payment_start: Boolean
)