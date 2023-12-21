package com.example.jayasales.model

data class SearchRouteDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<Datum>
)

data class DataSearch (
    val uid: String,
    val name: String
)