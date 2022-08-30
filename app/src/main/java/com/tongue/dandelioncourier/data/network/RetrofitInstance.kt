package com.tongue.dandelioncourier.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{

        val BASE_URL = "http://192.168.100.29:8088/"

        private val retrofitInstance by lazy {

            //val logging = HttpLoggingInterceptor()
            //logging.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                //.addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        }

        val shippingApi: ApiInterface by lazy(){
            retrofitInstance.create(ApiInterface::class.java)
        }

    }
}