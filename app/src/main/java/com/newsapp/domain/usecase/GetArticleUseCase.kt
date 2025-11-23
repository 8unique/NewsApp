package com.newsapp.domain.usecase

import com.newsapp.domain.model.Article
import com.newsapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetArticleUseCase(
    private val repository: Repository
) {
    operator fun invoke(url: String): Flow<Article?> {
        return repository.getArticleByUrl(url)
    }
}