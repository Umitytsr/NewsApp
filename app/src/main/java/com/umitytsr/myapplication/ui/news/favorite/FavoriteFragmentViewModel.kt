package com.umitytsr.myapplication.ui.news.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.data.repo.NewsAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteFragmentViewModel @Inject constructor(
    private val newsAppRepository: NewsAppRepository
): ViewModel() {

    private val _propertiesFavorite = MutableStateFlow<List<Article>>(listOf())
    val propertiesFavorite : StateFlow<List<Article>> = _propertiesFavorite.asStateFlow()

    fun getAllNewsFavorites(){
        viewModelScope.launch(Dispatchers.IO) {
            newsAppRepository.allNewsFavorites().collect{
                _propertiesFavorite.emit(it)
            }
        }
    }

}