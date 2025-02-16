package com.example.aroundegypt.domain.usecase

import com.example.aroundegypt.domain.model.Experience
import com.example.aroundegypt.domain.repo.ExperienceDatabaseRepo
import javax.inject.Inject

class AddRecentExperiencesToDatabaseUseCase @Inject constructor(private val recentExperienceDatabaseRepo: ExperienceDatabaseRepo) {
    suspend fun execute(recentExperiences: List<Experience>) =
        recentExperienceDatabaseRepo.insertRecentExperiences(recentExperiences)
}