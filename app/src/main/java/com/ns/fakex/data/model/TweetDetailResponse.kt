package com.ns.fakex.data.model

import com.google.gson.annotations.SerializedName

data class TweetDetailResponse(
    @SerializedName("data")
    val data: List<Detail>?,
    @SerializedName("includes")
    val includes: Includes?
)

data class Detail(
    @SerializedName("id")
    val id: String,
    @SerializedName("text")
    val text: String?,
    @SerializedName("created_at")
    val createdAt: String?,
)

data class TweetDetailItem(
    val text: String?,
    val authorName: String? = null,
    val authorUsername: String? = null,
    val createdAt: String? = null,
    val authorPhotoUrl: String? = null
)


