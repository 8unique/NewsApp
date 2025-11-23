package com.newsapp.di

import com.newsapp.data.repository.RepositoryImpl
import com.newsapp.domain.repository.Repository
import com.newsapp.domain.usecase.LoginUseCase
import com.newsapp.presentation.viewmodels.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Repository> { RepositoryImpl() }

    factory { LoginUseCase(get()) }

    viewModel { LoginViewModel(get()) }
}