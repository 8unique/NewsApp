package com.newsapp.domain.repository

interface Repository {
    suspend fun login(email: String, password: String): Result<Unit>
}