package com.newsapp.data.repository

import com.newsapp.domain.repository.Repository
import kotlinx.coroutines.delay

class RepositoryImpl(
) : Repository {
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
}