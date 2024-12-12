package com.ns.fakex.feature.tweetdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ns.fakex.R
import com.ns.fakex.base.BaseErrorPopUp
import com.ns.fakex.feature.list.viewmodel.TweetListViewEvent
import com.ns.fakex.feature.tweetdetail.viewmodel.TweetDetailViewEvent
import com.ns.fakex.feature.tweetdetail.viewmodel.TweetDetailViewModel
import com.ns.fakex.ui.theme.FakeXTheme
import com.ns.fakex.utils.formatTweetDate

@Composable
fun TweetDetailScreen(
    id: String,
    navigateBack: () -> Unit,
    viewModel: TweetDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onTriggerEvent(TweetDetailViewEvent.GetTweetDetail(id))
    }

    LaunchedEffect(uiState.navigateBack) {
        if (uiState.navigateBack)
            navigateBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.errorMessage.isNullOrEmpty().not()) {
            BaseErrorPopUp(errorMessage = uiState.errorMessage) {
                viewModel.onTriggerEvent(TweetDetailViewEvent.DismissErrorDialog)
            }
        }

        TopBar { viewModel.onTriggerEvent(TweetDetailViewEvent.OnNavigateBack) }

        uiState.tweetDetail?.let {
            UserContent(
                modifier = Modifier.padding(horizontal = 8.dp),
                imageUrl = it.authorPhotoUrl,
                name = it.authorName,
                userName = it.authorUsername
            )
            TweetContent(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = it.text,
                createdAt = it.createdAt
            )

        } ?: if (uiState.loading.not()) {
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = stringResource(R.string.post_not_found),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            AnimatedVisibility(uiState.loading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 8.dp))
            }
        }

    }
}

@Composable
fun TopBar(onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onClick() },
                painter = painterResource(R.drawable.ic_nav_back),
                tint = Color.White,
                contentDescription = "navigate back"
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.post),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }

        HorizontalDivider(thickness = (0.5).dp, color = Color.Gray)
    }
}

@Composable
fun UserContent(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    name: String?,
    userName: String?
) {
    Row(
        modifier = modifier.padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(border = BorderStroke(width = 1.dp, Color.Gray), shape = CircleShape),
            model = imageUrl,
            contentDescription = "user image"
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = name ?: "unknown",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "@$userName", style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun TweetContent(modifier: Modifier = Modifier, text: String?, createdAt: String?) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text ?: "",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(thickness = (0.5).dp, color = Color.Gray)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = createdAt?.formatTweetDate() ?: "", style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Preview
@Composable
fun TopBarPreview() {
    FakeXTheme {
        TopBar { }
    }
}

@Preview
@Composable
fun UserContentPreview() {
    FakeXTheme {
        UserContent(imageUrl = "", name = "Nisa Cezo", userName = "nisasakars")
    }
}

@Preview
@Composable
private fun TweetContentPreview() {
    FakeXTheme {
        TweetContent(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. \n\n" +
                    "Pellentesque quis lacus vel massa commodo accumsan. Sed eget est urna. Ut hendrerit felis vel tempus ornare.",
            createdAt = "2013-12-14T04:35:55.000Z"
        )
    }
}