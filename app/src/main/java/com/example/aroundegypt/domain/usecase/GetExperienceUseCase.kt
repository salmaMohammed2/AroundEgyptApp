package com.example.aroundegypt.domain.usecase

import com.example.aroundegypt.domain.repo.ExperienceRepo
import javax.inject.Inject

class GetExperienceUseCase @Inject constructor(private val experienceRepo: ExperienceRepo) {
    suspend fun execute(id: String) = experienceRepo.getSingleExperience(id)
}