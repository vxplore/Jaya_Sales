package com.example.jayasales.model

data class RouteDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<Datum>
)

data class Datum (
    val uid: String,
    val name: String
)