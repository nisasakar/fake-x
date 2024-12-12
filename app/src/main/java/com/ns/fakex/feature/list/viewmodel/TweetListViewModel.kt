package com.ns.fakex.feature.list.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ns.fakex.base.BaseViewModel
import com.ns.fakex.data.model.TweetEntity
import com.ns.fakex.data.resource.Resource
import com.ns.fakex.data.resource.asResource
import com.ns.fakex.data.usecase.AuthTokenUseCase
import com.ns.fakex.data.usecase.DatabaseOperationsUseCase
import com.ns.fakex.data.usecase.GetTimelineUseCase
import com.ns.fakex.data.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TweetListViewModel @Inject constructor(
    private val databaseOperationsUseCase: DatabaseOperationsUseCase
) : BaseViewModel<TweetListViewState, TweetListViewEvent>() {

    override fun createInitialState(): TweetListViewState =
        TweetListViewState()

    override fun onTriggerEvent(event: TweetListViewEvent) {
        when (event) {
            is TweetListViewEvent.DismissErrorDialog -> dismissErrorDialog()
        }
    }

    init {
        getTweets()
    }

    private fun getTweets() {
        viewModelScope.launch {
            databaseOperationsUseCase.invoke().asResource().onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        setState {
                            copy(
                                showEmptyContent = false,
                                tweets = resource.data,
                                loading = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        setState { copy(loading = true) }
                    }

                    is Resource.Error -> {
                        setState {
                            copy(
                                loading = false,
                                errorMessage = resource.exception?.message
                            )
                        }
                        Log.e("TweetList", resource.exception?.message.toString())
                    }
                }
            }.launchIn(this)
        }
    }

    private fun dismissErrorDialog() {
        setState { copy(errorMessage = null, showEmptyContent = true) }
    }
}
