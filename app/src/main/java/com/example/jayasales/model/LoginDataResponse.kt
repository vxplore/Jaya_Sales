package com.example.jayasales.model

data class LoginDataResponse (
    val status: Boolean,
    val message: String,
    val data: Data
)

data class Data (
    val user_id: String,
    val name: String,
    val phone: String,
    val email: String
)