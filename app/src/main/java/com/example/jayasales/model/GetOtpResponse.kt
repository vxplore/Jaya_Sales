package com.example.jayasales.model

data class GetOtpResponse (
    val status: Boolean,
    val message: String,
    val otp: Long
)

