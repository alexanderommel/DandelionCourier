package com.tongue.dandelioncourier.data.model

data class ShippingNotification(
    val origin: Position,
    val shippingId: String,
    val artifactId: String,
    val artifactResource: String,
    val accessToken: String
) {
}