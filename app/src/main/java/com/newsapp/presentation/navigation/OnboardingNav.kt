package com.newsapp.presentation.navigation

import com.newsapp.R
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class OnboardingNav(val route: String) {
    object LoginScreen : OnboardingNav("login_screen")
    object SignUpScreen : OnboardingNav("signup_screen")
}
sealed class BottomBarNav(val route: String, val title: Int, val icon: Int){
    data object HomeScreen : BottomBarNav("home_screen", R.string.bottombar_nav_home, R.drawable.ic_home)
    data object FavouriteScreen : BottomBarNav("favourite_screen", R.string.bottombar_nav_favourite, R.drawable.ic_favourite)
    data object ProfileScreen : BottomBarNav("profile_screen", R.string.bottombar_nav_profile, R.drawable.ic_profile)
}

sealed class HomeScreenNav(val route: String) {
    object ArticleDetail : HomeScreenNav("article_detail/{articleUrl}") {
        fun createRoute(articleUrl: String): String {
            val encodedUrl = URLEncoder.encode(articleUrl, StandardCharsets.UTF_8.toString())
            return "article_detail/$encodedUrl"
        }
    }
}