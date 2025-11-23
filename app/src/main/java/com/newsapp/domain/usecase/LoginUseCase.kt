package com.newsapp.domain.usecase

import com.newsapp.domain.repository.Repository

class LoginUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return try {
            repository.login(email, password)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}