package com.newsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.newsapp.presentation.screens.ArticleScreen
import com.newsapp.presentation.screens.FavouritesScreen
import com.newsapp.presentation.screens.HomeScreen
import com.newsapp.presentation.screens.ProfileScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarNav.HomeScreen.route
    ) {
        composable(route = BottomBarNav.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(route = BottomBarNav.FavouriteScreen.route) {
            FavouritesScreen(navController = navController)
        }

        composable(route = BottomBarNav.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }

        composable(
            route = Screen.ArticleDetail.route,
            arguments = listOf(
                navArgument("articleUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("articleUrl") ?: ""
            val articleUrl = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString())
            ArticleScreen(
                articleUrl = articleUrl,
                navController = navController
            )
        }
    }
}

sealed class Screen(val route: String) {
    object ArticleDetail : Screen("article_detail/{articleUrl}") {
        fun createRoute(articleUrl: String): String {
            val encodedUrl = URLEncoder.encode(articleUrl, StandardCharsets.UTF_8.toString())
            return "article_detail/$encodedUrl"
        }
    }
}