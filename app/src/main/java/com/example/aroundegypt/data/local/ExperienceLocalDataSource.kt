package com.example.aroundegypt.data.local

import com.example.aroundegypt.domain.entities.RecommendedExperienceEntity
import com.example.aroundegypt.domain.model.Experience

interface ExperienceLocalDataSource {
    suspend fun insertRecentExperiences(recentExperiences: List<Experience>)
    suspend fun getRecentExperiences(): List<Experience>
    suspend fun insertRecommendedExperiences(recommendedExperiences: List<RecommendedExperienceEntity>)
    suspend fun getRecommendedExperiences(): List<RecommendedExperienceEntity>
    suspend fun clearRecentExperienceTable()
    suspend fun clearRecommendedExperienceTable()
    suspend fun updateRecentExperience(id: String, likes: Int, isLiked: Boolean)
    suspend fun updateRecommendedExperience(id: String, likes: Int, isLiked: Boolean)
}