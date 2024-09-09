package com.umitytsr.myapplication.data.source.local

import com.umitytsr.myapplication.data.db.NewsDao
import com.umitytsr.myapplication.data.model.Article
import javax.inject.Inject

class NewsAppLocalDataSource @Inject constructor(private val newsDao: NewsDao) {
    suspend fun insertFavoriteProperties(favoriteProperties: Article){
        newsDao.insertNewsFavoriteProperties(favoriteProperties)
    }

    suspend fun deleteFavoriteProperties(favoriteProperties: Article){
        newsDao.deleteNewsFavorite(favoriteProperties)
    }

    suspend fun getAllNewsFavoritePropertiesFromDb(): List<Article>{
        return newsDao.getAllNewsFavorite()
    }

    suspend fun isFavorite(urlToImage:String): Boolean{
        return newsDao.isFavorite(urlToImage)
    }
}