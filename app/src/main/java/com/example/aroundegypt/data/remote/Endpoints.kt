package com.example.aroundegypt.data.remote

object Endpoints {
    const val RECOMMENDED_EXPERIENCES = "/api/v2/experiences?filter[recommended]=true"
    const val RECENT_EXPERIENCES = "/api/v2/experiences"
    const val SEARCH_EXPERIENCES = "/api/v2/experiences"
    const val SINGLE_EXPERIENCE = "/api/v2/experiences/{id}"
    const val LIKE_AN_EXPERIENCE = "/api/v2/experiences/{id}/like"
}