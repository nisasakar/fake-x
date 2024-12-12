package com.ns.fakex.feature.tweetdetail.viewmodel

import com.ns.fakex.base.IViewEvent

sealed class TweetDetailViewEvent : IViewEvent {
    data object OnNavigateBack : TweetDetailViewEvent()
    data object DismissErrorDialog : TweetDetailViewEvent()
    data class GetTweetDetail(val id: String) : TweetDetailViewEvent()
}