package com.ns.fakex.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class TimelineResponse(
    @SerializedName("data")
    val data: List<Tweet>,
    @SerializedName("includes")
    val includes: Includes?,
    @SerializedName("meta")
    val meta: Meta?
)

data class Includes(
    @SerializedName("users")
    val users: List<User>? = null
)

data class Tweet(
    @SerializedName("id")
    val id: String,
    @SerializedName("author_id")
    val authorId: String?,
    @SerializedName("text")
    val text: String? = null
)

data class Meta(
    @SerializedName("resultCount")
    val resultCount: Int? = null,
    @SerializedName("nextToken")
    val nextToken: String? = null
)

@Entity(tableName = "tweet_items")
data class TweetEntity(
    @PrimaryKey
    val id: String,
    val text: String? = null,
    val authorName: String? = null,
    val authorImageUrl: String? = null
)
