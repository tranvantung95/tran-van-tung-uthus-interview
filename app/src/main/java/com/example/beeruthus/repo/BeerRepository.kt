package com.example.beeruthus.repo

import com.example.beeruthus.network.ApiClient
import com.example.beeruthus.network.AppResult
import com.example.beeruthus.model.BeerResponse
import com.example.beeruthus.network.safeApiCall

class BeerRepository {
    private val api = ApiClient.retrofit

    suspend fun getBeer(page: Int, limit: Int): AppResult<BeerResponse>? {
        return safeApiCall {
            api.getBeers(page, limit)
        }
    }
}