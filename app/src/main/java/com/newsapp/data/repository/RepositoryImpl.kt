package com.newsapp.data.repository

import com.newsapp.data.local.SharedPreferences
import com.newsapp.data.local.dao.NewsDao
import com.newsapp.data.local.dao.UserDao
import com.newsapp.data.local.entity.UserEntity
import com.newsapp.data.mapper.toDomain
import com.newsapp.data.mapper.toDomainDirect
import com.newsapp.data.mapper.toEntity
import com.newsapp.data.remote.ApiInterface
import com.newsapp.data.remote.request.SignUpRequest
import com.newsapp.domain.model.Article
import com.newsapp.domain.model.User
import com.newsapp.domain.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val apiService: ApiInterface,
    private val newsDao: NewsDao,
    private val userDao: UserDao,
    private val sharedPreferences: SharedPreferences,
    private val apiKey: String
) : Repository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val userEntity = userDao.login(email, password)

            if (userEntity != null) {
                val user = userEntity.toDomain()
                sharedPreferences.saveUser(
                    userId = user.id,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    email = user.email
                )
                Result.success(user)
            } else {
                Result.failure(Exception("Invalid email or password"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Login failed: ${e.message}"))
        }
    }

    override suspend fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<User> {
        return try {
            val existingUser = userDao.getUserByEmail(email)

            if (existingUser != null) {
                return Result.failure(Exception("Email already exists"))
            }

            val userEntity = UserEntity(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password
            )

            val userId = userDao.insertUser(userEntity)

            val user = User(
                id = userId.toInt(),
                firstName = firstName,
                lastName = lastName,
                email = email
            )

            sharedPreferences.saveUser(
                userId = user.id,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email
            )

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(Exception("Sign up failed: ${e.message}"))
        }
    }

    override suspend fun logout() {
        sharedPreferences.clearUser()
    }

    override fun isLoggedIn(): Boolean {
        return sharedPreferences.isLoggedIn()
    }

    override fun getCurrentUser(): User? {
        val userId = sharedPreferences.getUserId()
        val firstName = sharedPreferences.getFirstName()
        val lastName = sharedPreferences.getLastName()
        val email = sharedPreferences.getEmail()

        return if (userId != -1 && firstName != null && lastName != null && email != null) {
            User(
                id = userId,
                firstName = firstName,
                lastName = lastName,
                email = email
            )
        } else {
            null
        }
    }

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


    override fun getArticleByUrl(url: String): Flow<Article?> {
        return newsDao.getArticle(url).map { entity ->
            entity?.toDomain()
        }
    }
}