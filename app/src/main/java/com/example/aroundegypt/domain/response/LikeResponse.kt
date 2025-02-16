package com.example.aroundegypt.domain.response

import com.google.gson.annotations.SerializedName

data class LikeResponse(
    val experienceId: String,
    @SerializedName("data") val data: Int? = null
)
