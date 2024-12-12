package com.ns.fakex.feature.list.viewmodel

import com.ns.fakex.base.IViewEvent

sealed class TweetListViewEvent: IViewEvent {
    data object DismissErrorDialog: TweetListViewEvent()
}