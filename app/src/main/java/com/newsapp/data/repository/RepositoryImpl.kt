package com.newsapp.data.repository

import com.newsapp.data.local.dao.NewsDao
import com.newsapp.data.mapper.toDomain
import com.newsapp.data.mapper.toDomainDirect
import com.newsapp.data.mapper.toEntity
import com.newsapp.data.remote.ApiInterface
import com.newsapp.domain.model.Article
import com.newsapp.domain.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val apiService: ApiInterface,
    private val newsDao: NewsDao,
    private val apiKey: String
) : Repository {

    override suspend fun getTopHeadlines(category: String?, page: Int): Result<List<Article>> {
        return try {
            val response = apiService.getTopHeadlines(
                category = category,
                page = page,
                apiKey = apiKey
            )

            if (response.isSuccessful && response.body() != null) {
                val responseBody = response.body()!!
                val articleEntities = responseBody.articles?.map { dto ->
                    dto.toEntity(category ?: "general")
                } ?: emptyList()

                if (page == 1) {
                    newsDao.deleteAllArticles()
                }
                newsDao.insertArticles(articleEntities)

                val domainArticles = articleEntities.map { entity ->
                    entity.toDomain()
                }
                Result.success(domainArticles)
            } else {
                Result.failure(Exception("Failed to fetch news: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchNews(query: String, page: Int): Result<List<Article>> {
        return try {
            val response = apiService.searchNews(
                query = query,
                page = page,
                apiKey = apiKey
            )

            if (response.isSuccessful && response.body() != null) {
                val responseBody = response.body()!!
                val domainArticles = responseBody.articles?.map { dto ->
                    dto.toDomainDirect()
                } ?: emptyList()
                Result.success(domainArticles)
            } else {
                Result.failure(Exception("Failed to search news: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCachedArticles(): Flow<List<Article>> {
        return newsDao.getAllArticles().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override fun getFavoriteArticles(): Flow<List<Article>> {
        return newsDao.getFavoriteArticles().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override suspend fun toggleFavorite(url: String, isFavorite: Boolean) {
        newsDao.updateFavorite(url, isFavorite)
    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            delay(1000)

            if (email.isNotEmpty() && password.isNotEmpty()) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Email and password are required"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getArticleByUrl(url: String): Flow<Article?> {
        return newsDao.getArticle(url).map { entity ->
            entity?.toDomain()
        }
    }
}