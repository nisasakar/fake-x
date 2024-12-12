package com.ns.fakex.feature.login.viewmodel

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
class LoginViewModel @Inject constructor(
    private val authTokenUseCase: AuthTokenUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getTimelineUseCase: GetTimelineUseCase,
    private val databaseOperationsUseCase: DatabaseOperationsUseCase
) : BaseViewModel<LoginViewState, LoginViewEvent>() {

    override fun createInitialState(): LoginViewState =
        LoginViewState()

    override fun onTriggerEvent(event: LoginViewEvent) {
        when (event) {
            is LoginViewEvent.SaveAuthToken -> saveAuthToken(event.authToken)
            is LoginViewEvent.DismissErrorDialog -> dismissErrorDialog()
        }
    }

    private fun saveAuthToken(authToken: String) {
        viewModelScope.launch {
            authTokenUseCase.invoke(authToken).asResource().onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        getUser()
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
                        Log.e("Login", resource.exception?.message.toString())
                    }
                }
            }.launchIn(this)
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase.invoke().asResource().onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data.data?.id?.let {
                            getTimeline(userId = it)
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
                        Log.e("Login", resource.exception?.message.toString())
                    }
                }
            }.launchIn(this)
        }
    }

    private fun getTimeline(userId: String) {
        viewModelScope.launch {
            getTimelineUseCase.invoke(userId = userId).asResource()
                .onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            val usersMap = resource.data.includes?.users
                            val tweets = mutableListOf<TweetEntity>()
                            resource.data.data.forEach { tweet ->
                                val author = usersMap?.find { it.id == tweet.authorId }
                                tweets.add(
                                    TweetEntity(
                                        id = tweet.id,
                                        text = tweet.text,
                                        authorName = author?.name,
                                        authorImageUrl = author?.userPhotoUrl
                                    )
                                )
                            }
                            saveTweetsToDb(tweets)
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
                            Log.e("Login", resource.exception?.message.toString())
                        }
                    }
                }.launchIn(this)
        }
    }

    private fun saveTweetsToDb(list: List<TweetEntity>) {
        viewModelScope.launch {
            databaseOperationsUseCase.invoke(list).asResource().onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        setState { copy(navigateToTweetList = true) }
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
                        Log.e("Login", resource.exception?.message.toString())
                    }
                }
            }.launchIn(this)
        }
    }

    private fun dismissErrorDialog() {
        setState { copy(errorMessage = null, navigateToTweetList = true) }
    }
}
