package com.ns.fakex.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ns.fakex.feature.login.LoginScreen

const val LoginRoute = "login_route"

fun NavController.navigateToLogin(
    navOptions: NavOptions? = null
) {
    this.navigate(LoginRoute, navOptions)
}

fun NavGraphBuilder.loginScreen(navigateToTweetList: () -> Unit) {
    composable(
        route = LoginRoute
    ) {
        LoginScreen(navigateToTweetList = navigateToTweetList)
    }
}