package com.newsapp.domain.usecase

import com.newsapp.domain.repository.Repository

class IsLoggedInUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Boolean {
        return repository.isLoggedIn()
    }
}