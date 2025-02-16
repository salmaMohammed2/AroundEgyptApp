package com.example.aroundegypt.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aroundegypt.domain.entities.RecommendedExperienceEntity
import com.example.aroundegypt.domain.model.Experience

@Database(
    entities = [Experience::class, RecommendedExperienceEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ExperienceDatabase : RoomDatabase() {
    abstract fun getRecentExperienceDao(): RecentExperienceDao
    abstract fun getRecommendedExperienceDao(): RecommendedExperienceDao
}