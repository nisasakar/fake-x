package com.ns.fakex.feature.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ns.fakex.R
import com.ns.fakex.base.BaseErrorPopUp
import com.ns.fakex.data.model.TweetEntity
import com.ns.fakex.feature.list.viewmodel.TweetListViewEvent
import com.ns.fakex.feature.list.viewmodel.TweetListViewModel
import com.ns.fakex.feature.login.viewmodel.LoginViewEvent
import com.ns.fakex.ui.theme.FakeXTheme

@Composable
fun TweetListScreen(
    navigateToDetail: (String) -> Unit,
    viewModel: TweetListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        AnimatedVisibility(uiState.loading) {
            CircularProgressIndicator()
        }

        if (uiState.errorMessage.isNullOrEmpty().not()) {
            BaseErrorPopUp(errorMessage = uiState.errorMessage) {
                viewModel.onTriggerEvent(TweetListViewEvent.DismissErrorDialog)
            }
        }

        if (!uiState.showEmptyContent && !uiState.tweets.isNullOrEmpty()) {
            TweetListContent(
                modifier = Modifier.fillMaxSize(),
                list = uiState.tweets ?: emptyList(),
                onItemClick = { tweetId ->
                    navigateToDetail(tweetId)
                }
            )
        } else {
            EmptyContent(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun EmptyContent(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.empty_content_text),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center
    )
}

@Composable
fun TweetListContent(
    modifier: Modifier = Modifier,
    list: List<TweetEntity>,
    onItemClick: (String) -> Unit
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(list) { index, tweet ->
            TweetContent(item = tweet, onItemClick = onItemClick)
            HorizontalDivider(color = Color.Gray)
        }
    }
}

@Composable
fun TweetContent(modifier: Modifier = Modifier, item: TweetEntity, onItemClick: (String) -> Unit) {
    Row(modifier = modifier
        .padding(vertical = 6.dp)
        .clickable { onItemClick(item.id) }) {
        AsyncImage(
            modifier = Modifier
                .padding(4.dp)
                .size(32.dp)
                .clip(CircleShape)
                .border(border = BorderStroke(width = 1.dp, Color.Gray), shape = CircleShape),
            model = item.authorImageUrl,
            contentDescription = "user image"
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = item.authorName ?: "unknown",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.text ?: "unknown", style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
fun EmptyContentPreview() {
    FakeXTheme {
        EmptyContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }
}

@Preview
@Composable
private fun TweetContentPreview() {
    FakeXTheme {
        TweetContent(
            item = TweetEntity(id = "1", text = "deneme", authorName = "nisasakars", ""),
            onItemClick = {})
    }
}

@Preview
@Composable
private fun TweetListContentPreview() {
    FakeXTheme {
        TweetListContent(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            list = listOf(
                TweetEntity(
                    id = "1",
                    text = "deneme",
                    authorName = "nisasakars",
                    ""
                ),
                TweetEntity(
                    id = "1",
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque quis lacus vel massa commodo accumsan. Sed eget est urna. Ut hendrerit felis vel tempus ornare.",
                    authorName = "nisasakars",
                    ""
                ),
                TweetEntity(
                    id = "1",
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque quis lacus vel massa commodo accumsan. Sed eget est urna. Ut hendrerit felis vel tempus ornare.",
                    authorName = "nisasakars",
                    ""
                )
            ),
            onItemClick = {}
        )
    }
}