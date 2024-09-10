package com.umitytsr.myapplication.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.data.service.NewsAPIService

class NewsPagingSource(
    private val newsAPIService: NewsAPIService
): PagingSource<Int,Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val pageNumber = params.key ?: 1
        return try {
            val response = newsAPIService.getHeadlines("us",pageNumber)
            val article = response.articles

            LoadResult.Page(
                data = article,
                prevKey = if (pageNumber ==1) null else pageNumber - 1,
                nextKey = if (article.isEmpty()) null else pageNumber + 1
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

}