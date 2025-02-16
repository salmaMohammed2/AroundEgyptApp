package com.example.aroundegypt.domain.response

import com.example.aroundegypt.domain.model.Experience
import com.google.gson.annotations.SerializedName

data class ExperienceResponse(
    @SerializedName("data") val experiences: List<Experience>
)
