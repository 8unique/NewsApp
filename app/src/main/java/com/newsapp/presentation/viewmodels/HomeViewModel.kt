package com.newsapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.domain.model.Article
import com.newsapp.domain.usecase.FavoriteUseCase
import com.newsapp.domain.usecase.GetHeadlinesUseCase
import com.newsapp.domain.usecase.SearchUseCase
import com.newsapp.presentation.screens.uistates.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class HomeViewModel(
    private val getTopHeadlinesUseCase: GetHeadlinesUseCase,
    private val searchNewsUseCase: SearchUseCase,
    private val toggleFavoriteUseCase: FavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadNews()
    }

    fun loadNews(category: String = "general", page: Int = 1) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = getTopHeadlinesUseCase(category, page)

            result.fold(
                onSuccess = { articles ->
                    _uiState.value = _uiState.value.copy(
                        articles = if (page == 1) articles else _uiState.value.articles + articles,
                        isLoading = false,
                        selectedCategory = category,
                        currentPage = page,
                        hasMorePages = articles.isNotEmpty()
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
            )
        }
    }

    fun searchNews(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = searchNewsUseCase(query, 1)

            result.fold(
                onSuccess = { articles ->
                    _uiState.value = _uiState.value.copy(
                        articles = articles,
                        isLoading = false,
                        currentPage = 1
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
            )
        }
    }

    fun loadNextPage() {
        if (!_uiState.value.isLoading && _uiState.value.hasMorePages) {
            loadNews(_uiState.value.selectedCategory, _uiState.value.currentPage + 1)
        }
    }

    fun toggleFavorite(article: Article) {
        viewModelScope.launch {
            toggleFavoriteUseCase(article.url, !article.isFavorite)
        }
    }

    fun selectCategory(category: String) {
        loadNews(category, 1)
    }
}