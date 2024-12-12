package com.ns.fakex.main.viewmodel

import androidx.compose.runtime.Stable
import com.ns.fakex.base.IViewState

@Stable
data class MainViewState(
    val loading: Boolean = false
) : IViewState