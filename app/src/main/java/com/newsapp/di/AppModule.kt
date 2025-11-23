package com.newsapp.di

import androidx.room.Room
import com.newsapp.data.local.SharedPreferences
import com.newsapp.data.local.database.DataBase
import com.newsapp.data.remote.ApiInterface
import com.newsapp.data.repository.RepositoryImpl
import com.newsapp.domain.repository.Repository
import com.newsapp.domain.usecase.*
import com.newsapp.presentation.viewmodels.ArticleViewModel
import com.newsapp.presentation.viewmodels.FavouritesViewModel
import com.newsapp.presentation.viewmodels.HomeViewModel
import com.newsapp.presentation.viewmodels.LoginViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            DataBase::class.java,
            "news_database"
        ).build()
    }

    single { get<DataBase>().newsDao() }
    single { get<DataBase>().userDao() }
    single { SharedPreferences(androidContext()) }

    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(ApiInterface::class.java)
    }

    single { "8b28af9fc9794709a28d54ee82e87d8e" } // api key

    single<Repository> {
        RepositoryImpl(
            apiService = get(),
            newsDao = get(),
            userDao = get(),
            sharedPreferences = get(),
            apiKey = get()
        )
    }

    factory { GetHeadlinesUseCase(get()) }
    factory { SearchUseCase(get()) }
    factory { GetCachedArticlesUseCase(get()) }
    factory { FavoriteUseCase(get()) }
    factory { GetArticleUseCase( get()) }
    factory { GetFavoriteArticlesUseCase(get()) }
    factory { IsLoggedInUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { SignUpUseCase(get()) }
    factory { GetCurrentUserUseCase(get()) }

    viewModel {
        LoginViewModel(
            loginUseCase = get(),
            signUpUseCase = get()
        )
    }

    viewModel {
        HomeViewModel(
            getTopHeadlinesUseCase = get(),
            searchNewsUseCase = get(),
            toggleFavoriteUseCase = get()
        )
    }

    viewModel {
        ArticleViewModel(
            getArticleByUrlUseCase = get(),
            toggleFavoriteUseCase = get()
        )
    }
    viewModel {
        FavouritesViewModel(
            getFavoriteArticlesUseCase = get()
        )
    }
}