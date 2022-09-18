package com.tongue.dandelioncourier.data

import com.tongue.dandelioncourier.data.model.Driver
import com.tongue.dandelioncourier.data.model.DriverAuthentication
import com.tongue.dandelioncourier.data.model.RegistrationForm
import com.tongue.dandelioncourier.data.network.RetrofitInstance
import com.tongue.dandelioncourier.utils.AppLog

class DriverRemoteRepository {

    companion object{
        const val TAG = "DriverRemoteRepository"
    }

    suspend fun login(driver: Driver): DriverAuthentication{
        AppLog.d(TAG,"Login")
        val response = RetrofitInstance.shippingApi.login(driver)
        if (!response.ok){
            AppLog.d(TAG,"Unsatisfied request")
            throw Exception("Unsatisfied request")
        }
        AppLog.d(TAG,"ok")
        return response.success.payload
    }

    suspend fun initState(jwt: String): String{
        AppLog.i(TAG,"Init State")
        val response = RetrofitInstance.shippingApi.initDriverState(jwt)
        if (!response.ok){
            AppLog.d(TAG,"Unsatisfied request")
            throw Exception("Unsatisfied request")
        }
        AppLog.d(TAG,"ok")
        return response.success.payload
    }

    suspend fun register(registrationForm: RegistrationForm): Driver{
        AppLog.i(TAG,"Register")
        val response = RetrofitInstance.shippingApi.register(registrationForm)
        if (!response.ok){
            AppLog.d(TAG,"Unsatisfied request")
            throw Exception("Unsatisfied request")
        }
        AppLog.d(TAG,"ok")
        return response.success.payload
    }


}