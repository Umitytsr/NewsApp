package com.umitytsr.myapplication.data.service

import com.umitytsr.myapplication.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {
    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("country") countryCode: String = "us",
        @Query("page") pageNumber: Int = 1
    ): NewsResponse

    @GET("everything")
    suspend fun searchForNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int = 1
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getCategory(
        @Query("category") category: String = "business"
    ): NewsResponse
}