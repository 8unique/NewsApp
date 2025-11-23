package com.newsapp.domain.model

data class Article(
    val url: String,
    val sourceName: String,
    val author: String?,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    val category: String = "general",
    val isFavorite: Boolean = false
)
