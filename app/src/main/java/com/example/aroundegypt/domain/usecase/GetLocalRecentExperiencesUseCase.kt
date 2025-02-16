package com.example.aroundegypt.domain.usecase

import com.example.aroundegypt.domain.repo.ExperienceDatabaseRepo
import javax.inject.Inject

class GetLocalRecentExperiencesUseCase @Inject constructor(private val recentExperienceDatabaseRepo: ExperienceDatabaseRepo) {
    suspend fun execute() = recentExperienceDatabaseRepo.getRecentExperiences()
}