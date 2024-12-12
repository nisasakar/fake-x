package com.ns.fakex.feature.tweetdetail.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ns.fakex.base.BaseViewModel
import com.ns.fakex.data.model.TweetDetailItem
import com.ns.fakex.data.resource.Resource
import com.ns.fakex.data.resource.asResource
import com.ns.fakex.data.usecase.GetTweetDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TweetDetailViewModel @Inject constructor(private val getTweetDetailUseCase: GetTweetDetailUseCase) :
    BaseViewModel<TweetDetailViewState, TweetDetailViewEvent>() {
    override fun createInitialState(): TweetDetailViewState = TweetDetailViewState()

    override fun onTriggerEvent(event: TweetDetailViewEvent) {
        when (event) {
            is TweetDetailViewEvent.OnNavigateBack -> onNavigateBack()
            is TweetDetailViewEvent.DismissErrorDialog -> dismissErrorDialog()
            is TweetDetailViewEvent.GetTweetDetail -> getTweetDetail(event.id)
        }
    }

    private fun getTweetDetail(id: String) {
        viewModelScope.launch {
            getTweetDetailUseCase.invoke(id).asResource().onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data.let { response ->
                            setState {
                                copy(
                                    tweetDetail = TweetDetailItem(
                                        text = response.data?.firstOrNull()?.text,
                                        authorName = response.includes?.users?.firstOrNull()?.name,
                                        authorUsername = response.includes?.users?.firstOrNull()?.username,
                                        authorPhotoUrl = response.includes?.users?.firstOrNull()?.userPhotoUrl,
                                        createdAt = response.data?.firstOrNull()?.createdAt
                                    )
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        setState { copy(loading = true) }
                    }

                    is Resource.Error -> {setState { copy(loading = false, errorMessage = resource.exception?.message) }
                        Log.e("TweetDetail", resource.exception?.message.toString())}
                }
            }.launchIn(this)
        }
    }

    private fun onNavigateBack() {
        setState { copy(navigateBack = true) }
    }

    private fun dismissErrorDialog() {
        setState { copy(errorMessage = null) }
    }
}