package com.example.pghunter.network

import com.example.pghunter.model.PGItem
import com.example.pghunter.model.PGResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Retrofit interface for API calls
interface ApiService {

    // Fetch all PGs
    @GET("allpg")
    fun getAllPGs(): Call<PGResponse>


}


