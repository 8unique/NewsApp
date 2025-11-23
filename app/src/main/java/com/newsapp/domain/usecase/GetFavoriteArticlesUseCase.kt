package com.newsapp.domain.usecase

import com.newsapp.domain.model.Article
import com.newsapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetFavoriteArticlesUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<List<Article>> {
        return repository.getFavoriteArticles()
    }
}