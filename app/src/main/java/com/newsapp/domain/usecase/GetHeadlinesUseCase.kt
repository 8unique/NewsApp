package com.newsapp.domain.usecase

import com.newsapp.domain.model.Article
import com.newsapp.domain.repository.Repository

class GetHeadlinesUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(category: String? = null, page: Int = 1): Result<List<Article>> {
        return repository.getTopHeadlines(category, page)
    }
}

