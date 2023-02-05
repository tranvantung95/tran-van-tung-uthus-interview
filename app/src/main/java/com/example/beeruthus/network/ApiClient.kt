package com.example.beeruthus.network


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val interceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://apps.uthus.vn")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}