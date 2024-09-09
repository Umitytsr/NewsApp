package com.umitytsr.myapplication.ui.news.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.data.repo.NewsAppRepository
import com.umitytsr.myapplication.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(private val newsAppRepository: NewsAppRepository): ViewModel(){

    private val _propertiesSearchNews = MutableStateFlow<Resource<List<Article>>>(Resource.Loading)
    val propertiesSearchNews : StateFlow<Resource<List<Article>>> = _propertiesSearchNews.asStateFlow()


    fun getSearchNews(q:String){
        viewModelScope.launch {
            newsAppRepository.fetchSearchNews(q).collectLatest {
                _propertiesSearchNews.emit(it)
            }
        }
    }
}