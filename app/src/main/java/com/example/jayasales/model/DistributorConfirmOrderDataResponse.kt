package com.example.jayasales.model

data class DistributorConfirmOrderDataResponse (
    val status: Boolean,
    val data: List<ConfirmOrderDatum>
)

data class ConfirmOrderDatum (
    val order_id: String,
    val distributor_id: String,
    val created_at: String,
    val distributor: ConfirmOrderDistributor,
    val location: ConfirmOrderLocation,
    val items: Long,
    val track_status: String,
    val track_order: TrackOrder
)

data class ConfirmOrderDistributor (
    val name: String,
    val phone: String,
    val email: String
)

data class ConfirmOrderLocation (
    val location_id: String,
    val name: String,
    val pincode: String,
    val state: String
)

data class TrackOrder (
    val booked: Booked,
    val ready_to_load: Booked,
    val dispatched: Booked,
    val received: Booked
)

data class Booked (
    val status: Boolean,
    val datetime: String
)
