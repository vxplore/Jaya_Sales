package com.example.jayasales.model

data class ReasonDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<ReasonDatum>
)

data class ReasonDatum (
    val uid: String,
    val value: String
)