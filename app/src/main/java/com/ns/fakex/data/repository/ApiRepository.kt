package com.ns.fakex.data.repository

import com.ns.fakex.data.model.TimelineResponse
import com.ns.fakex.data.model.TweetDetailResponse
import com.ns.fakex.data.model.UserResponse

interface ApiRepository {
    suspend fun getCurrentUser(): Result<UserResponse>

    suspend fun getUserTimeline(userId: String): Result<TimelineResponse>

    suspend fun getTweetDetail(id: String): Result<TweetDetailResponse>
}