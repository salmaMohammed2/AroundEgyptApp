package com.example.aroundegypt.domain.usecase

import com.example.aroundegypt.domain.repo.ExperienceRepo
import javax.inject.Inject

class LikeExperienceUseCase @Inject constructor(private val experienceRepo: ExperienceRepo) {
    suspend fun execute(id: String) = experienceRepo.postLikeAnExperience(id)
}