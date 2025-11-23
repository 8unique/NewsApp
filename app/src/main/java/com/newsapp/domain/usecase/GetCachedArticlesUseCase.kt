package com.newsapp.domain.usecase

import com.newsapp.domain.repository.Repository

class GetCachedArticlesUseCase(
    private val repository: Repository
) {
    operator fun invoke() = repository.getCachedArticles()
}