package com.ns.fakex.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val data: User? = null
)

data class User(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("profile_image_url")
    val userPhotoUrl: String? = null
)
