package com.ns.fakex.data.remote

import com.ns.fakex.data.model.TimelineResponse
import com.ns.fakex.data.model.TweetDetailResponse
import com.ns.fakex.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("2/users/me")
    suspend fun getCurrentUser(): Response<UserResponse>

    @GET("2/users/{id}/timelines/reverse_chronological")
    suspend fun getUserTimeline(
        @Path("id") userId: String,
        @Query("max_results") maxResults: Int = 25,
        @Query("expansions") expansions: String? = "attachments.media_keys,author_id",
        @Query("media.fields") mediaFields: String? = "url,preview_image_url",
        @Query("tweet.fields") tweetFields: String? = "created_at,text",
    ): Response<TimelineResponse>

    @GET("2/tweets")
    suspend fun getTweetDetail(
        @Query("ids") id: String,
        @Query("tweet.fields") tweetFields: String = "created_at",
        @Query("expansions") expansions: String = "author_id",
        @Query("user.fields") userFields: String = "created_at"
    ): Response<TweetDetailResponse>
}