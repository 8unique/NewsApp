package com.newsapp.domain.usecase

import com.newsapp.domain.repository.Repository

class FavoriteUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(url: String, isFavorite: Boolean) {
        repository.toggleFavorite(url, isFavorite)
    }
}