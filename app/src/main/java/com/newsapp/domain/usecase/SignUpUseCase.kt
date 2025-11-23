package com.newsapp.domain.usecase

import com.newsapp.domain.model.User
import com.newsapp.domain.repository.Repository

class SignUpUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<User> {
        return try {
            when {
                firstName.isBlank() -> return Result.failure(Exception("First name is required"))
                lastName.isBlank() -> return Result.failure(Exception("Last name is required"))
                email.isBlank() -> return Result.failure(Exception("Email is required"))
                password.isBlank() -> return Result.failure(Exception("Password is required"))
                password.length < 6 -> return Result.failure(Exception("Password must be at least 6 characters"))
            }
            repository.signUp(firstName, lastName, email, password)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}