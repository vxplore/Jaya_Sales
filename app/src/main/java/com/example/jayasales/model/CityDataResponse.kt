package com.example.jayasales.model

data class CityDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<CityDatum>
)

data class CityDatum (
    val uid: String,
    val name: String
)