package com.example.aroundegypt.data.repo

import com.example.aroundegypt.data.local.ExperienceLocalDataSource
import com.example.aroundegypt.domain.entities.RecommendedExperienceEntity
import com.example.aroundegypt.domain.model.Experience
import com.example.aroundegypt.domain.repo.ExperienceDatabaseRepo
import javax.inject.Inject

class ExperienceDatabaseRepoImpl @Inject constructor(private val experienceLocalDataSource: ExperienceLocalDataSource) :
    ExperienceDatabaseRepo {
    override suspend fun insertRecentExperiences(recentExperiences: List<Experience>) {
        experienceLocalDataSource.insertRecentExperiences(recentExperiences)
    }

    override suspend fun getRecentExperiences(): List<Experience> {
        return experienceLocalDataSource.getRecentExperiences()
    }

    override suspend fun insertRecommendedExperiences(recommendedExperiences: List<RecommendedExperienceEntity>) {
        experienceLocalDataSource.insertRecommendedExperiences(recommendedExperiences)
    }

    override suspend fun getRecommendedExperiences(): List<RecommendedExperienceEntity> {
        return experienceLocalDataSource.getRecommendedExperiences()
    }

    override suspend fun clearRecentExperienceTable() {
        experienceLocalDataSource.clearRecentExperienceTable()
    }

    override suspend fun clearRecommendedExperienceTable() {
        experienceLocalDataSource.clearRecommendedExperienceTable()
    }

    override suspend fun updateRecentExperience(id: String, likes: Int, isLiked: Boolean) {
        experienceLocalDataSource.updateRecentExperience(id, likes, isLiked)
    }

    override suspend fun updateRecommendedExperience(id: String, likes: Int, isLiked: Boolean) {
        experienceLocalDataSource.updateRecommendedExperience(id, likes, isLiked)
    }
}