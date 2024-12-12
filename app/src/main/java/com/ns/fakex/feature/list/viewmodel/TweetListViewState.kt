package com.ns.fakex.feature.list.viewmodel

import androidx.compose.runtime.Stable
import androidx.datastore.preferences.protobuf.Internal.BooleanList
import com.ns.fakex.base.IViewState
import com.ns.fakex.data.model.TweetEntity

@Stable
data class TweetListViewState(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val showEmptyContent: Boolean = false,
    val tweets: List<TweetEntity>? = null
) : IViewState