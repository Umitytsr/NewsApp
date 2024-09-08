package com.umitytsr.myapplication.ui.news.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.data.repo.NewsAppRepository
import com.umitytsr.myapplication.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val newsAppRepository: NewsAppRepository): ViewModel() {
    private val _propertiesNews = MutableStateFlow<Resource<List<Article>>>(Resource.Loading)
    val propertiesNews : StateFlow<Resource<List<Article>>> = _propertiesNews.asStateFlow()

    private val _propertiesCategoryNews = MutableStateFlow<Resource<List<Article>>>(Resource.Loading)
    val propertiesCategoryNews = _propertiesCategoryNews.asStateFlow()

    init {
        getAllNews()
        getAllCategoryNews("business")
    }

    private fun getAllNews (){
        viewModelScope.launch(Dispatchers.IO) {
            newsAppRepository.fetchAllNews().collect{
                _propertiesNews.emit(it)
            }
        }
    }

    fun getAllCategoryNews(category:String){
        viewModelScope.launch(Dispatchers.IO) {
            newsAppRepository.fetchAllCategoryNews(category).collect{
                _propertiesCategoryNews.emit(it)
            }
        }
    }
}