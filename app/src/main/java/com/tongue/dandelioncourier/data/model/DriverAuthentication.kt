package com.tongue.dandelioncourier.data.model

data class DriverAuthentication(
    val jwt: String,
    val driver: Driver
) {
}