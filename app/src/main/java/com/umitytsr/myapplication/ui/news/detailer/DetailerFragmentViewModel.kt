package com.umitytsr.myapplication.ui.news.detailer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.data.repo.NewsAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailerFragmentViewModel @Inject constructor(
    private val newsAppRepository: NewsAppRepository
) : ViewModel() {

    private val _isFavoriteProperties = MutableStateFlow<Boolean>(false)
    val isFavoriteProperties : StateFlow<Boolean> = _isFavoriteProperties

    fun isFavorite(urlToImage: String){
        viewModelScope.launch {
            newsAppRepository.isFavorite(urlToImage).collect{
                _isFavoriteProperties.emit(it)
            }
        }
    }

    fun addNewsToFavorites(news:Article){
        viewModelScope.launch {
            newsAppRepository.addNewsToFavorites(news)
        }
    }

    fun deleteNewsFromFavorites(news: Article){
        viewModelScope.launch {
            newsAppRepository.deleteNewsFromFavorites(news)
        }
    }
}