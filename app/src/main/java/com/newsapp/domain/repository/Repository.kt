package com.newsapp.domain.repository

import com.newsapp.domain.model.Article
import com.newsapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun signUp(firstName: String, lastName: String, email: String, password: String): Result<User>
    suspend fun logout()
    fun isLoggedIn(): Boolean
    fun getCurrentUser(): User?
    suspend fun getTopHeadlines(category: String?, page: Int): Result<List<Article>>
    suspend fun searchNews(query: String, page: Int): Result<List<Article>>
    fun getCachedArticles(): Flow<List<Article>>
    fun getFavoriteArticles(): Flow<List<Article>>
    suspend fun toggleFavorite(url: String, isFavorite: Boolean)
    fun getArticleByUrl(url: String): Flow<Article?>
}