package com.tongue.dandelioncourier.data.domain

data class Authentication(
    val jwt: String,
    val username: String,
    val name: String
)