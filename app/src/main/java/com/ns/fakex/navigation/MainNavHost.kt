package com.ns.fakex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.ns.fakex.feature.list.navigation.navigateToTweetList
import com.ns.fakex.feature.list.navigation.tweetListScreen
import com.ns.fakex.feature.login.navigation.loginScreen
import com.ns.fakex.feature.login.navigation.navigateToLogin
import com.ns.fakex.feature.splash.navigation.SplashRoute
import com.ns.fakex.feature.splash.navigation.splashScreen
import com.ns.fakex.feature.tweetdetail.navigation.navigateToTweetDetail
import com.ns.fakex.feature.tweetdetail.navigation.tweetDetailScreen
import com.ns.fakex.main.MainAppState
import com.ns.fakex.main.rememberMainAppState

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = SplashRoute,
    appState: MainAppState = rememberMainAppState(),
) {
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = startDestination,
    ) {
        splashScreen(
            navigateToLogin = {
                appState.navController.navigateToLogin(
                    navOptions = navOptions {
                        popUpTo(SplashRoute) { inclusive = false }
                    })
            },
            navigateToTweetList = {
                appState.navController.navigateToTweetList(
                    navOptions = navOptions {
                        popUpTo(SplashRoute) { inclusive = false }
                    })
            })
        loginScreen(navigateToTweetList = {
            appState.navController.navigateToTweetList(
                navOptions = navOptions {
                    popUpTo(SplashRoute) { inclusive = false }
                })
        })
        tweetListScreen(navigateToDetail = { tweetId ->
            appState.navController.navigateToTweetDetail(tweetId = tweetId)
        })
        tweetDetailScreen { appState.navController.navigateUp() }
    }
}