package com.newsapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.domain.model.Article
import com.newsapp.domain.usecase.GetFavoriteArticlesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FavouritesViewModel(
    getFavoriteArticlesUseCase: GetFavoriteArticlesUseCase
) : ViewModel() {

    val favoriteArticles: StateFlow<List<Article>> =
        getFavoriteArticlesUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )


}