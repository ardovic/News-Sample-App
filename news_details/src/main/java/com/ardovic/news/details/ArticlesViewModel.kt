package com.ardovic.news.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ardovic.news.api.Article
import com.ardovic.news.api.NewsService
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider

internal class ArticlesViewModel(private val newsService: NewsService) : ViewModel() {

    val articles = flow {
        emit(newsService.topHeadlines("ru").articles)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    class Factory @Inject constructor(
        private val newsService: Provider<NewsService>
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == ArticlesViewModel::class.java)
            return ArticlesViewModel(newsService.get()) as T
        }
    }
}
