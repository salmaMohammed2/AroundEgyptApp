package com.example.aroundegypt.domain.usecase

import com.example.aroundegypt.domain.repo.ExperienceDatabaseRepo
import javax.inject.Inject

class GetLocalRecommendedExperiencesUseCase @Inject constructor(private val recommendedExperienceDatabaseRepo: ExperienceDatabaseRepo) {
    suspend fun execute() = recommendedExperienceDatabaseRepo.getRecommendedExperiences()
}