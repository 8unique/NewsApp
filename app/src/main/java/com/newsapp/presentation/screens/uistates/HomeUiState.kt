package com.newsapp.presentation.screens.uistates

import com.newsapp.domain.model.Article

data class HomeUiState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCategory: String = "general",
    val currentPage: Int = 1,
    val hasMorePages: Boolean = true
)
