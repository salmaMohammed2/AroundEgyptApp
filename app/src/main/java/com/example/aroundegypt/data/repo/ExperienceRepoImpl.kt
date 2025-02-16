package com.example.aroundegypt.data.repo

import com.example.aroundegypt.data.remote.ExperienceRemoteDataSource
import com.example.aroundegypt.domain.repo.ExperienceRepo
import javax.inject.Inject

class ExperienceRepoImpl @Inject constructor(private val experienceRemoteDataSource: ExperienceRemoteDataSource) :
    ExperienceRepo {
    override suspend fun getRecommendedExperiences() =
        experienceRemoteDataSource.getRecommendedExperiences()

    override suspend fun getRecentExperiences() = experienceRemoteDataSource.getRecentExperiences()

    override suspend fun getSearchExperiences(searchWord: String) =
        experienceRemoteDataSource.getSearchExperiences(searchWord)

    override suspend fun getSingleExperience(id: String) =
        experienceRemoteDataSource.getSingleExperience(id)

    override suspend fun postLikeAnExperience(id: String) =
        experienceRemoteDataSource.postLikeAnExperience(id)
}