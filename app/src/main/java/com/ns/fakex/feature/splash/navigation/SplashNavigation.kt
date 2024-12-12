package com.ns.fakex.feature.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ns.fakex.feature.splash.SplashScreen

const val SplashRoute = "splash_route"

fun NavGraphBuilder.splashScreen(
    navigateToLogin: () -> Unit,
    navigateToTweetList: () -> Unit
) {
    composable(route = SplashRoute) {
        SplashScreen(navigateToLogin = navigateToLogin, navigateToTweetList = navigateToTweetList)
    }
}