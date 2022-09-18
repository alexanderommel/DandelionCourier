package com.tongue.dandelioncourier.data.network

sealed class NetworkResult<T: Any> {
    class Success<T: Any>(val payload: T): NetworkResult<T>()
    class Error<T: Any>(val httpCode: Int, val message: String?): NetworkResult<T>()
    class Exception<T: Any>(val e: Throwable): NetworkResult<T>()
}