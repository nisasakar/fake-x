package com.ns.fakex.feature.login.viewmodel

import com.ns.fakex.base.IViewEvent

sealed class LoginViewEvent : IViewEvent {
    data class SaveAuthToken(val authToken: String): LoginViewEvent()
    data object DismissErrorDialog: LoginViewEvent()
}
