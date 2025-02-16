package com.example.aroundegypt.data.local

import com.example.aroundegypt.data.local.database.RecentExperienceDao
import com.example.aroundegypt.data.local.database.RecommendedExperienceDao
import com.example.aroundegypt.domain.entities.RecommendedExperienceEntity
import com.example.aroundegypt.domain.model.Experience
import javax.inject.Inject

class ExperienceLocalDataSourceImpl @Inject constructor(
    private val recentExperienceDao: RecentExperienceDao,
    private val recommendedExperienceDao: RecommendedExperienceDao
) :
    ExperienceLocalDataSource {
    override suspend fun insertRecentExperiences(recentExperiences: List<Experience>) {
        recentExperienceDao.insertRecentExperiences(recentExperiences)
    }

    override suspend fun getRecentExperiences(): List<Experience> {
        return recentExperienceDao.getAllRecentExperiences()
    }

    override suspend fun insertRecommendedExperiences(recommendedExperiences: List<RecommendedExperienceEntity>) {
        recommendedExperienceDao.insertRecommendedExperiences(recommendedExperiences)
    }

    override suspend fun getRecommendedExperiences(): List<RecommendedExperienceEntity> {
        return recommendedExperienceDao.getAllRecommendedExperiences()
    }

    override suspend fun clearRecentExperienceTable() {
        recentExperienceDao.clearTable()
    }

    override suspend fun clearRecommendedExperienceTable() {
        recommendedExperienceDao.clearTable()
    }

    override suspend fun updateRecentExperience(id: String, likes: Int, isLiked: Boolean) {
        recentExperienceDao.updateExperienceLikes(id, likes, isLiked)
    }

    override suspend fun updateRecommendedExperience(id: String, likes: Int, isLiked: Boolean) {
        recommendedExperienceDao.updateExperienceLikes(id, likes, isLiked)
    }
}