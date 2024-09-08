package com.umitytsr.myapplication.data.source.remote

import com.umitytsr.myapplication.data.service.NewsAPIService
import javax.inject.Inject

class NewsAppRemoteDataSource @Inject constructor(private val newsAPIService: NewsAPIService) {
    suspend fun getAllNewsProperties() = newsAPIService.getHeadlines()
    suspend fun getAllSearchProperties(q:String) = newsAPIService.searchForNews(q)
    suspend fun getAllCategory(category: String) = newsAPIService.getCategory(category)
}