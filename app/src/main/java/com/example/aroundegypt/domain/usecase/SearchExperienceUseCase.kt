package com.example.aroundegypt.domain.usecase

import com.example.aroundegypt.domain.repo.ExperienceRepo
import javax.inject.Inject

class SearchExperienceUseCase @Inject constructor(private val experienceRepo: ExperienceRepo) {
    suspend fun execute(searchWord: String) = experienceRepo.getSearchExperiences(searchWord)
}