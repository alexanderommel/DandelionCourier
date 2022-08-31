package com.tongue.dandelioncourier.data.domain

data class DriverAuthentication(
    val jwt: String,
    val driver: Driver
) {
}