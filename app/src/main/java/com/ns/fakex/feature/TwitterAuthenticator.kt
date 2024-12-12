package com.ns.fakex.feature

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ns.fakex.utils.Constants.AUTHORIZATION_BASE_URL
import com.ns.fakex.utils.Constants.CLIENT_ID
import com.ns.fakex.utils.Constants.REDIRECT_URI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.Base64

class TwitterAuthenticator {
    fun initiateTwitterLogin(context: Context, codeChallenge: String) {
        val authorizationUrl = buildAuthorizationUrl(codeChallenge)

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authorizationUrl))
        context.startActivity(intent)
    }

    private fun buildAuthorizationUrl(codeChallenge: String): String {
        return "$AUTHORIZATION_BASE_URL?" +
                "response_type=code" +
                "&client_id=$CLIENT_ID" +
                "&redirect_uri=$REDIRECT_URI" +
                "&scope=tweet.read%20users.read" +
                "&state=state" +
                "&code_challenge=$codeChallenge" +
                "&code_challenge_method=S256"
    }
}

@Composable
fun TwitterLoginButton(onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text("Twitter ile Giriş Yap")
    }
}

@Composable
fun HandleTwitterCallback(onTokenReceived: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val uri = (context as? Activity)?.intent?.data
                uri?.let {
                    if (it.host == "example.com") {
                        val authorizationCode = uri.getQueryParameter("code")
                        authorizationCode?.let { code ->
                            onTokenReceived(code)
                        }
                    }
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}