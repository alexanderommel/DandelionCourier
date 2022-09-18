package com.tongue.dandelioncourier.data.model

data class Authentication(
    val jwt: String,
    val username: String,
    val name: String
)