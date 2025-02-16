package com.example.aroundegypt.data.remote.api

import com.example.aroundegypt.data.remote.Endpoints.LIKE_AN_EXPERIENCE
import com.example.aroundegypt.data.remote.Endpoints.RECENT_EXPERIENCES
import com.example.aroundegypt.data.remote.Endpoints.RECOMMENDED_EXPERIENCES
import com.example.aroundegypt.data.remote.Endpoints.SEARCH_EXPERIENCES
import com.example.aroundegypt.data.remote.Endpoints.SINGLE_EXPERIENCE
import com.example.aroundegypt.domain.model.Experience
import com.example.aroundegypt.domain.response.ExperienceResponse
import com.example.aroundegypt.domain.response.LikeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(RECOMMENDED_EXPERIENCES)
    suspend fun getRecommendedExperiences(): Response<ExperienceResponse>

    @GET(RECENT_EXPERIENCES)
    suspend fun getRecentExperiences(): Response<ExperienceResponse>

    @GET(SEARCH_EXPERIENCES)
    suspend fun getSearchExperiences(@Query("filter[title]") searchText: String): Response<ExperienceResponse>

    @GET(SINGLE_EXPERIENCE)
    suspend fun getSingleExperience(
        @Path("id") id: String
    ): Response<Experience>

    @POST(LIKE_AN_EXPERIENCE)
    suspend fun postLikeAnExperience(@Path("id") id: String): Response<LikeResponse>
}