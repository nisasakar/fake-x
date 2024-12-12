package com.ns.fakex.feature.tweetdetail.viewmodel

import androidx.compose.runtime.Stable
import com.ns.fakex.base.IViewState
import com.ns.fakex.data.model.TweetDetailItem

@Stable
data class TweetDetailViewState(
    val loading: Boolean = true,
    val errorMessage: String? = null,
    val navigateBack: Boolean = false,
    val tweetDetail: TweetDetailItem? = null
): IViewState
