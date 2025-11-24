# News App - Android

A news application built with Jetpack Compose, following Clean Architecture principles with MVVM pattern. The app provides users with the latest news from various categories, allows them to save favorite articles, and includes a complete authentication system.

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpack-compose&logoColor=white)

## Features

### Authentication
- **User Registration**: Create new account with email and password
- **Login System**: Secure login with credentials stored in Room Database
- **Session Management**: Persistent login state with SharedPreferences
- **Profile Screen**: View user information

### News Features
- **Latest News Feed**: Browse top headlines from various sources
- **Category Filtering**: Filter news by categories (Health, Technology, Finance, Art, Sports, General)
- **Search Functionality**: Search for specific news articles
- **Article Details**: Read full article content with rich media
- **Favorites System**: Save articles to favorites with persistent storage
- **Offline Support**: Cached articles available offline
- **Pagination**: Infinite scroll with automatic loading

### UI/UX
- **Modern Material Design 3**: Clean and intuitive interface
- **Smooth Animations**: Floating label animations, page transitions
- **Bottom Navigation**: Easy navigation between main sections
- **Floating Action Buttons**: Quick access to favorite functionality

## Architecture

### Clean Architecture Layers
```
app/
app/data/
app/data/local/
app/data/local/dao/                # Room Database DAOs
app/data/local/database/           # Database configuration
app/data/local/entity/             # Database entities
app/data/local/SharedPreferences.kt

app/data/remote/                   # Retrofit API interface
app/data/remote/response/          # API response models

app/data/repository/               # Repository implementations
app/data/mapper/                   # Data mappers

app/domain/
app/domain/model/                  # Domain models
app/domain/repository/             # Repository
app/domain/usecase/                # use cases

app/presentation/
app/presentation/screens/          # Composable screens
app/presentation/components/       # UI components
app/presentation/viewmodels/       # ViewModels
app/presentation/navigation/       # Navigation

```

## Tech Stack

### Core
- **Kotlin**
- **Jetpack Compose**
- **Coroutines & Flow**
- **Material Design 3**

### Architecture Components
- **ViewModel**
- **Navigation Component**
- **Room Database**
- **SharedPreferences**

### Dependency Injection
- **Koin**

### Networking
- **Retrofit**
- **OkHttp**
- **Gson**

### Image Loading
- **Coil**

### API
- **News API** ([newsapi.org](https://newsapi.org))

## Dependencies
```gradle
// Compose
implementation "androidx.compose.ui:ui:1.5.4"
implementation "androidx.compose.material3:material3:1.1.2"
implementation "androidx.navigation:navigation-compose:2.7.5"

// Koin
implementation "io.insert-koin:koin-android:3.5.0"
implementation "io.insert-koin:koin-androidx-compose:3.5.0"

// Retrofit
implementation "com.squareup.retrofit2:retrofit:2.9.0"
implementation "com.squareup.retrofit2:converter-gson:2.9.0"
implementation "com.squareup.okhttp3:logging-interceptor:4.12.0"

// Room
implementation "androidx.room:room-runtime:2.6.0"
implementation "androidx.room:room-ktx:2.6.0"
kapt "androidx.room:room-compiler:2.6.0"

// Coroutines
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"

// Coil
implementation "io.coil-kt:coil-compose:2.5.0"

// Paging
implementation "androidx.paging:paging-runtime:3.2.1"
implementation "androidx.paging:paging-compose:3.2.1"
```
