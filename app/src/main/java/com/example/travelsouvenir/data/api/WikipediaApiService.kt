package com.example.travelsouvenir.data.api

import retrofit2.http.GET
import retrofit2.http.Path

interface WikipediaApiService {
    @GET("page/summary/{title}")
    suspend fun getPlaceInfo(@Path("title") title: String): WikipediaPlaceResponse
}
