package com.tongue.dandelioncourier.data.network

import retrofit2.HttpException
import retrofit2.Response

class RetrofitResultHandler {
    companion object{
        fun <T: Any>handleHttpResponse(execute: () -> Response<T>): NetworkResult<T>{
            return try {
                val response = execute()
                val body = response.body()
                if (response.isSuccessful && body!=null){
                    NetworkResult.Success(body)
                }else{
                    NetworkResult.Error(response.code(), message = response.message())
                }
            }catch (e: HttpException){
                NetworkResult.Error(e.code(), message = e.message())
            }catch (e: Throwable){
                NetworkResult.Exception(e)
            }
        }
    }
}