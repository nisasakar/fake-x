package com.ns.fakex.feature.list.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ns.fakex.feature.list.TweetListScreen

const val TweetListRoute = "tweet_list_route"

fun NavController.navigateToTweetList(
    navOptions: NavOptions? = null
) {
    this.navigate(TweetListRoute, navOptions)
}

fun NavGraphBuilder.tweetListScreen(navigateToDetail: (String) -> Unit) {
    composable(route = TweetListRoute) {
        TweetListScreen(navigateToDetail = navigateToDetail)
    }
}