package com.newsapp.domain.usecase

import com.newsapp.domain.model.User
import com.newsapp.domain.repository.Repository

class LoginUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return try {
            if (email.isBlank() || password.isBlank()) {
                return Result.failure(Exception("Email and password are required"))
            }
            repository.login(email, password)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}