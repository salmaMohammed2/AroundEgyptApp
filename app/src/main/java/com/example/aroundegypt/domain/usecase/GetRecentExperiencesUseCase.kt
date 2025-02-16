package com.example.aroundegypt.domain.usecase

import com.example.aroundegypt.domain.repo.ExperienceRepo
import javax.inject.Inject

class GetRecentExperiencesUseCase @Inject constructor(private val experienceRepo: ExperienceRepo) {
    suspend fun execute() = experienceRepo.getRecentExperiences()
}