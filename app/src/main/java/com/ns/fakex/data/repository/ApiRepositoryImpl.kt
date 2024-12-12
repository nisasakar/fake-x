package com.ns.fakex.data.repository

import com.ns.fakex.data.model.TimelineResponse
import com.ns.fakex.data.model.TweetDetailResponse
import com.ns.fakex.data.model.UserResponse
import com.ns.fakex.data.remote.ApiService
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(private val remoteService: ApiService) : ApiRepository {

    override suspend fun getCurrentUser(): Result<UserResponse> {
        return runCatching {
            val response = remoteService.getCurrentUser()
            if (response.isSuccessful) {
                response.body() ?: throw IllegalStateException("Response body is null")
            } else {
                throw IllegalArgumentException("Error: ${response.errorBody()?.string()}")
            }
        }
    }

    override suspend fun getUserTimeline(userId: String): Result<TimelineResponse> {
        return runCatching {
            val response = remoteService.getUserTimeline(userId = userId)
            if (response.isSuccessful) {
                response.body() ?: throw IllegalStateException("Response body is null")
            } else {
                throw IllegalArgumentException("Error: ${response.errorBody()?.string()}")
            }
        }
    }

    override suspend fun getTweetDetail(id: String): Result<TweetDetailResponse> {
        return runCatching {
            val response = remoteService.getTweetDetail(id = id)
            if (response.isSuccessful) {
                response.body() ?: throw IllegalStateException("Response body is null")
            } else {
                throw IllegalArgumentException("Error: ${response.errorBody()?.string()}")
            }
        }
    }
}
