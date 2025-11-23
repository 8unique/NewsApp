package com.newsapp.domain.usecase

import com.newsapp.domain.model.Article
import com.newsapp.domain.repository.Repository

class SearchUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(query: String, page: Int = 1): Result<List<Article>> {
        return repository.searchNews(query, page)
    }
}


