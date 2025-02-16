package com.example.aroundegypt.domain.usecase

import com.example.aroundegypt.domain.entities.RecommendedExperienceEntity
import com.example.aroundegypt.domain.repo.ExperienceDatabaseRepo
import javax.inject.Inject

class AddRecommendedExperiencesToDatabaseUseCase @Inject constructor(private val recommendedExperienceDatabaseRepo: ExperienceDatabaseRepo) {
    suspend fun execute(recommendedExperiences: List<RecommendedExperienceEntity>) =
        recommendedExperienceDatabaseRepo.insertRecommendedExperiences(recommendedExperiences)
}