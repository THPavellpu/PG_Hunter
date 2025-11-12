package com.example.pghunter.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object ApiClient {

    private const val BASE_URL = "https://pg-connect-hptb.onrender.com/api/v1/pg/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS) // ⏱️ connection timeout
        .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)    // ⏱️ read timeout
        .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)   // ⏱️ write timeout
        .build()


    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
