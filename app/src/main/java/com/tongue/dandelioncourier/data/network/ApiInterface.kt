package com.tongue.dandelioncourier.data.network

import com.tongue.dandelioncourier.data.model.Driver
import com.tongue.dandelioncourier.data.model.DriverAuthentication
import com.tongue.dandelioncourier.data.model.RegistrationForm
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @POST("/drivers/oauth")
    suspend fun login(@Body driver: Driver): ApiResponse<DriverAuthentication>

    @Headers("Accept:application/json","Content-Type:application/json")
    @POST("/drivers/jwt")
    suspend fun initDriverState(@Header("Authorization") jwt: String): ApiResponse<String>

    @POST("/drivers/register")
    suspend fun register(@Body registrationForm: RegistrationForm): ApiResponse<Driver>

}