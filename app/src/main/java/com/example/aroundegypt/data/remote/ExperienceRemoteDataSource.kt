package com.example.aroundegypt.data.remote

import com.example.aroundegypt.domain.model.Experience
import com.example.aroundegypt.domain.response.ExperienceResponse
import com.example.aroundegypt.domain.response.LikeResponse
import retrofit2.Response

interface ExperienceRemoteDataSource {
    suspend fun getRecommendedExperiences(): Response<ExperienceResponse>
    suspend fun getRecentExperiences(): Response<ExperienceResponse>
    suspend fun getSearchExperiences(searchWord: String): Response<ExperienceResponse>
    suspend fun getSingleExperience(id: String): Response<Experience>
    suspend fun postLikeAnExperience(id: String): Response<LikeResponse>
}