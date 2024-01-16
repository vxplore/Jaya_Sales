package com.example.jayasales.model

data class AttendanceDataResponse (
    val status: Boolean,
    val data: Attendance
)

data class Attendance (
    val check_in: String,
    val check_out: String
)