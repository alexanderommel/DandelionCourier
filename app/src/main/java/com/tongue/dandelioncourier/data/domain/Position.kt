package com.tongue.dandelioncourier.data.domain

data class Position(
    var latitude: Float,
    var longitude: Float,
    var address: String,
    var owner: String
)