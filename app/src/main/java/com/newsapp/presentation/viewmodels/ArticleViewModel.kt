package com.newsapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.domain.model.Article
import com.newsapp.domain.usecase.FavoriteUseCase
import com.newsapp.domain.usecase.GetArticleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val getArticleByUrlUseCase: GetArticleUseCase,
    private val toggleFavoriteUseCase: FavoriteUseCase
) : ViewModel() {

    private val _article = MutableStateFlow<Article?>(null)
    val article: StateFlow<Article?> = _article.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun loadArticle(articleUrl: String) {
        viewModelScope.launch {
            getArticleByUrlUseCase(articleUrl).collect { loadedArticle ->
                _article.value = loadedArticle
                _isFavorite.value = loadedArticle?.isFavorite ?: false
            }
        }
    }

    fun toggleFavorite(article: Article) {
        viewModelScope.launch {
            val newFavoriteState = !_isFavorite.value
            toggleFavoriteUseCase(article.url, newFavoriteState)
            _isFavorite.value = newFavoriteState
        }
    }
}