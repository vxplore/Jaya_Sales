package com.example.jayasales.model

data class StateDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<StateDatum>
)

data class StateDatum (
    val uid: String,
    val name: String
)