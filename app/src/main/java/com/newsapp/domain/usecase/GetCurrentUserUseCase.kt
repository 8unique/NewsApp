package com.newsapp.domain.usecase

import com.newsapp.domain.model.User
import com.newsapp.domain.repository.Repository

class GetCurrentUserUseCase(
    private val repository: Repository
) {
    operator fun invoke(): User? {
        return repository.getCurrentUser()
    }
}