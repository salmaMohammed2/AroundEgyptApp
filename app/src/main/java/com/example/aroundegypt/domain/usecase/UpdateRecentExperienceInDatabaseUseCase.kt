package com.example.aroundegypt.domain.usecase

import com.example.aroundegypt.domain.repo.ExperienceDatabaseRepo
import javax.inject.Inject

class UpdateRecentExperienceInDatabaseUseCase @Inject constructor(private val recentExperienceDatabaseRepo: ExperienceDatabaseRepo) {
    suspend fun execute(id: String, likes: Int, isLiked: Boolean) =
        recentExperienceDatabaseRepo.updateRecentExperience(id, likes, isLiked)
}