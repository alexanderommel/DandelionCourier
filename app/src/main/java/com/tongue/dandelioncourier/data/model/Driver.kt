package com.tongue.dandelioncourier.data.model

data class Driver(
    val id: Long=0,
    val username: String="",
    val firstname: String="",
    val lastname: String="",
    val imageUrl: String="",
    val type: Type=Type.COURIER,
    val vehicleInfo: VehicleInfo = VehicleInfo()
) {
}

enum class Type{
    RIDER,COURIER
}