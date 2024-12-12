package com.ns.fakex.feature.splash.viewModel

import androidx.compose.runtime.Stable
import com.ns.fakex.base.IViewState

@Stable
data class SplashViewState(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val navigateToLogin: Boolean = false,
    val navigateToTweetList: Boolean = false,
) : IViewState