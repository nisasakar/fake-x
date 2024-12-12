package com.ns.fakex.feature.splash.viewModel

import com.ns.fakex.base.IViewEvent

sealed class SplashViewEvent : IViewEvent {
    data object DismissErrorDialog: SplashViewEvent()
}