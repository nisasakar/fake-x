package com.ns.fakex.feature.login.viewmodel

import androidx.compose.runtime.Stable
import com.ns.fakex.base.IViewState

@Stable
data class LoginViewState(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val navigateToTweetList: Boolean = false,
) : IViewState
