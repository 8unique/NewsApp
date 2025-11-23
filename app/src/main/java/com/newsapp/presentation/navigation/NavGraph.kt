package com.newsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.newsapp.presentation.screens.LoginScreen
import com.newsapp.presentation.screens.ProfileScreen
import com.newsapp.presentation.screens.SignUpScreen
import com.newsapp.presentation.screens.FavouritesScreen
import com.newsapp.presentation.screens.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = BottomBarNav.HomeScreen.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {


        composable(route = OnboardingNav.LoginScreen.route) {
            LoginScreen(
                onNavigateToLogin = {
                    navController.navigate(OnboardingNav.LoginScreen.route) {
                        popUpTo(OnboardingNav.LoginScreen.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = OnboardingNav.SignUpScreen.route) {
            SignUpScreen(
                onNavigateToSignUp = {
                    navController.navigate(OnboardingNav.SignUpScreen.route) {
                        popUpTo(OnboardingNav.SignUpScreen.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = BottomBarNav.HomeScreen.route) {
            HomeScreen(
                onNavigateToHome = {
                    navController.navigate(BottomBarNav.HomeScreen.route)
                }
            )
        }


        composable(route = BottomBarNav.ProfileScreen.route) {
            ProfileScreen(
                onNavigateToProfile = {
                    navController.navigate(BottomBarNav.ProfileScreen.route)
                }
            )
        }

        composable(route = BottomBarNav.FavouriteScreen.route) {
            FavouritesScreen(
                onNavigateToFavourites = {
                    navController.navigate(BottomBarNav.FavouriteScreen.route)
                }
            )
        }

    }
}