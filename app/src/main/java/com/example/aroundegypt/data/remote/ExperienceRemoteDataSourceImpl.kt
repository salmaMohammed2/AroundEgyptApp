package com.example.aroundegypt.data.remote

import com.example.aroundegypt.data.remote.api.RetrofitInstance
import com.example.aroundegypt.domain.model.Experience
import com.example.aroundegypt.domain.response.ExperienceResponse
import com.example.aroundegypt.domain.response.LikeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class ExperienceRemoteDataSourceImpl @Inject constructor() : ExperienceRemoteDataSource {
    override suspend fun getRecommendedExperiences(): Response<ExperienceResponse> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.getRecommendedExperiences()
        }
    }

    override suspend fun getRecentExperiences(): Response<ExperienceResponse> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.getRecentExperiences()
        }
    }

    override suspend fun getSearchExperiences(searchWord: String): Response<ExperienceResponse> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.getSearchExperiences(searchWord)
        }
    }

    override suspend fun getSingleExperience(id: String): Response<Experience> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.getSingleExperience(id)
        }
    }

    override suspend fun postLikeAnExperience(id: String): Response<LikeResponse> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.postLikeAnExperience(id)
        }
    }
}