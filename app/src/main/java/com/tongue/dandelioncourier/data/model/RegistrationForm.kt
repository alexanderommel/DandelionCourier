package com.tongue.dandelioncourier.data.model

data class RegistrationForm(
    val email: String,
    val firstName: String,
    val lastName: String,
    val car: String,
    val brand: String,
    val password: String
) {
}