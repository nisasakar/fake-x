package com.ns.fakex.feature.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ns.fakex.base.BaseErrorPopUp
import com.ns.fakex.feature.HandleTwitterCallback
import com.ns.fakex.feature.TwitterAuthenticator
import com.ns.fakex.feature.TwitterLoginButton
import com.ns.fakex.feature.login.viewmodel.LoginViewEvent
import com.ns.fakex.feature.login.viewmodel.LoginViewModel
import com.ns.fakex.ui.theme.FakeXTheme
import com.ns.fakex.utils.generateCodeChallenge
import com.ns.fakex.utils.generateCodeVerifier

@Composable
fun LoginScreen(
    navigateToTweetList: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToTweetList) {
        if (uiState.navigateToTweetList)
            navigateToTweetList()
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val context = LocalContext.current
        val authenticator = remember { TwitterAuthenticator() }

        AnimatedVisibility(uiState.loading) {
            CircularProgressIndicator()
        }

        if(uiState.errorMessage.isNullOrEmpty().not()) {
            BaseErrorPopUp(errorMessage = uiState.errorMessage) {
                viewModel.onTriggerEvent(LoginViewEvent.DismissErrorDialog)
            }
        }

        TwitterLoginButton(onClick = {
            val codeVerifier = generateCodeVerifier()
            val codeChallenge = generateCodeChallenge(codeVerifier)
            authenticator.initiateTwitterLogin(context, codeChallenge)
        })

        HandleTwitterCallback { authorizationCode ->
            viewModel.onTriggerEvent(LoginViewEvent.SaveAuthToken(authorizationCode))
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    FakeXTheme {
        LoginScreen(navigateToTweetList = {})
    }
}
