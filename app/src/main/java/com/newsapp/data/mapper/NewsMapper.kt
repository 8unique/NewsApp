package com.newsapp.data.mapper

import com.newsapp.data.local.entity.ArticleEntity
import com.newsapp.data.remote.response.HeadlinesResponse
import com.newsapp.domain.model.Article

fun HeadlinesResponse.Article.toEntity(category: String = "general"): ArticleEntity {
    return ArticleEntity(
        url = this.url ?: "",
        sourceName = this.source?.name ?: "Unknown",
        author = this.author,
        title = this.title ?: "",
        description = this.description,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt ?: "",
        content = this.content,
        category = category,
        isFavorite = false
    )
}

fun ArticleEntity.toDomain(): Article {
    return Article(
        url = this.url,
        sourceName = this.sourceName,
        author = this.author,
        title = this.title,
        description = this.description,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content,
        category = this.category,
        isFavorite = this.isFavorite
    )
}

fun HeadlinesResponse.Article.toDomainDirect(): Article {
    return Article(
        url = this.url ?: "",
        sourceName = this.source?.name ?: "Unknown",
        author = this.author,
        title = this.title ?: "",
        description = this.description,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt ?: "",
        content = this.content,
        category = "general",
        isFavorite = false
    )
}