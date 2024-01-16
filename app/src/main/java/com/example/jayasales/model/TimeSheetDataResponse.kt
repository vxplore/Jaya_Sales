package com.example.jayasales.model

data class TimeSheetDataResponse(
    val status: Boolean,
    val data: List<TimeSheet>
)

data class TimeSheet(
    val date: String,
    val check_in: Check,
    val check_out: Check,
    val working_hour: String
)

data class Check(
    val time: String,
    val lat: String,
    val lng: String
)

