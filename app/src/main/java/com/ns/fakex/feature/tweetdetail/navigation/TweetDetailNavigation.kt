package com.ns.fakex.feature.tweetdetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ns.fakex.feature.tweetdetail.TweetDetailScreen

const val TweetDetailRoute = "tweet_detail_route"
const val tweetIdArg = "tweet_id"

fun NavController.navigateToTweetDetail(
    tweetId: String,
    navOptions: NavOptions? = null
) {
    this.navigate(TweetDetailRoute.plus("/$tweetId"), navOptions)
}

fun NavGraphBuilder.tweetDetailScreen(navigateBack: () -> Unit) {
    composable(
        route = TweetDetailRoute.plus("/{$tweetIdArg}"),
        arguments = listOf(navArgument(tweetIdArg) { type = NavType.StringType })
    ) { backStackEntry ->
        val tweetId = backStackEntry.arguments?.getString(tweetIdArg)
        TweetDetailScreen(id = tweetId ?: "", navigateBack = navigateBack)
    }
}