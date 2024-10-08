package com.umitytsr.myapplication.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.data.service.NewsAPIService
import com.umitytsr.myapplication.data.source.local.NewsAppLocalDataSource
import com.umitytsr.myapplication.data.source.paging.NewsPagingSource
import com.umitytsr.myapplication.data.source.remote.NewsAppRemoteDataSource
import com.umitytsr.myapplication.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsAppRepository @Inject constructor(
    private val remoteDataSource: NewsAppRemoteDataSource,
    private val localDataSource: NewsAppLocalDataSource,
    private val newsAPIService: NewsAPIService
) {
    suspend fun fetchAllNews(): Flow<Resource<List<Article>>> = flow {
        Resource.Loading
        try {
            val propertiesNewsFromAPI = remoteDataSource.getAllNewsProperties().articles.filter {
                it.urlToImage != null
            }
            emit(Resource.Success(propertiesNewsFromAPI))
        }catch (e:Exception){
            Resource.Error(e)
        }
    }

    suspend fun fetchAllCategoryNews(category: String): Flow<Resource<List<Article>>> = flow {
        Resource.Loading
        try {
            val propertiesCategoryNewsFromAPI = remoteDataSource.getAllCategory(category).articles.filter {
                it.urlToImage != null
            }
            emit(Resource.Success(propertiesCategoryNewsFromAPI))
        }catch (e:Exception){
            Resource.Error(e)
        }
    }

    suspend fun fetchSearchNews(q:String):Flow<Resource<List<Article>>> = flow {
        Resource.Loading
        try {
            val propertiesSearchNewsFromAPI = remoteDataSource.getAllSearchProperties(q).articles.filter {
                it.urlToImage != null
            }
            emit(Resource.Success(propertiesSearchNewsFromAPI))
        }catch (e:Exception){
            Resource.Error(e)
        }
    }

    fun fetchPagingNews(): Flow<PagingData<Article>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {NewsPagingSource(newsAPIService)},
            initialKey = 1
        ).flow
    }

    suspend fun allNewsFavorites(): Flow<List<Article>> = flow{
        emit(localDataSource.getAllNewsFavoritePropertiesFromDb())
    }

    suspend fun addNewsToFavorites(favorite: Article){
        localDataSource.insertFavoriteProperties(favorite)
    }

    suspend fun deleteNewsFromFavorites(favorite: Article){
        localDataSource.deleteFavoriteProperties(favorite)
    }

    suspend fun isFavorite(urlToImage: String): Flow<Boolean> = flow {
        emit(localDataSource.isFavorite(urlToImage))
    }
}