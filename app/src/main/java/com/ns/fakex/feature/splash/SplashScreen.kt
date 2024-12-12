package com.ns.fakex.feature.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ns.fakex.base.BaseErrorPopUp
import com.ns.fakex.feature.splash.viewModel.SplashViewEvent
import com.ns.fakex.feature.splash.viewModel.SplashViewModel

@Composable
fun SplashScreen(
    navigateToLogin: () -> Unit, navigateToTweetList: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToLogin) {
        if (uiState.navigateToLogin) {
            navigateToLogin()
        }
    }

    LaunchedEffect(uiState.navigateToTweetList) {
        if (uiState.navigateToTweetList) {
            navigateToTweetList()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AnimatedVisibility(uiState.loading) {
            CircularProgressIndicator()
        }

        if(uiState.errorMessage.isNullOrEmpty().not()) {
            BaseErrorPopUp(errorMessage = uiState.errorMessage) {
                viewModel.onTriggerEvent(SplashViewEvent.DismissErrorDialog)
            }
        }
    }
}
