package com.example.aroundegypt.domain.usecase

import com.example.aroundegypt.domain.repo.ExperienceDatabaseRepo
import javax.inject.Inject

class UpdateRecommendedExperienceInDatabaseUseCase @Inject constructor(private val recommendedExperienceDatabaseRepo: ExperienceDatabaseRepo) {
    suspend fun execute(id: String, likes: Int, isLiked: Boolean) =
        recommendedExperienceDatabaseRepo.updateRecommendedExperience(id, likes, isLiked)
}