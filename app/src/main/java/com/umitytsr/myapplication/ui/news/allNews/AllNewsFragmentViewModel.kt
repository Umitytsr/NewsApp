package com.umitytsr.myapplication.ui.news.allNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.data.repo.NewsAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllNewsFragmentViewModel @Inject constructor(
    private val newsAppRepository: NewsAppRepository
) : ViewModel() {

    private val _propertiesNewsPaging = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val propertiesNewsPaging: StateFlow<PagingData<Article>> = _propertiesNewsPaging.asStateFlow()

    init {
        getNewsPaging()
    }

    private fun getNewsPaging() {
        viewModelScope.launch {
            newsAppRepository.fetchPagingNews()
                .cachedIn(viewModelScope)
                .collect {
                    _propertiesNewsPaging.emit(it)
                }
        }
    }
}