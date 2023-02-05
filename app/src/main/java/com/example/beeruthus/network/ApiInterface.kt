package com.example.beeruthus.network

import com.example.beeruthus.model.BeerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("api/api-testing/sample-data")
    suspend fun getBeers(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
    ): BeerResponse
}