package com.example.jayasales.model
data class DashboardDataResponse (
    val status: Boolean,
    val message: String,
    val data: DashBoardData
)

data class DashBoardData (
    val payments: String,
    val sells: Long
)