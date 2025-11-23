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
import com.newsapp.presentation.screens.LoginScreen
import com.newsapp.presentation.screens.ProfileScreen
import com.newsapp.presentation.screens.SignUpScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets



@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = OnboardingNav.LoginScreen.route
    ) {
        composable(route = OnboardingNav.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(route = OnboardingNav.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }

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
            route = HomeScreenNav.ArticleDetail.route,
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