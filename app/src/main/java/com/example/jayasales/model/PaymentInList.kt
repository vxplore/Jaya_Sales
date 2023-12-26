package com.example.jayasales.model

data class PaymentInList (
    val invoice_id: String,
    val amount: String,
    val date: String,
    val transaction_status: String,
    val time: String,
    val paymentDone:String
)