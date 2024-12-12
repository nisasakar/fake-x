package com.ns.fakex.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.ns.fakex.base.BaseViewModel
import com.ns.fakex.data.resource.Resource
import com.ns.fakex.data.resource.asResource
import com.ns.fakex.data.usecase.AuthTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() :
    BaseViewModel<MainViewState, MainViewEvent>() {
    override fun createInitialState(): MainViewState = MainViewState()

    override fun onTriggerEvent(event: MainViewEvent) {
    }

}