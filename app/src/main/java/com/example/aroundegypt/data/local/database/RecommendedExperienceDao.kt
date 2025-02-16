package com.example.aroundegypt.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aroundegypt.domain.entities.RecommendedExperienceEntity

@Dao
interface RecommendedExperienceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecommendedExperiences(recommendedExperiences: List<RecommendedExperienceEntity>)

    @Query("select * from recommended_experience_table")
    fun getAllRecommendedExperiences(): List<RecommendedExperienceEntity>

    @Query("DELETE from recommended_experience_table")
    fun clearTable()

    @Query("UPDATE RECOMMENDED_EXPERIENCE_TABLE SET likes_no = :likes, is_liked = :isLiked WHERE id = :id")
    fun updateExperienceLikes(id: String, likes: Int, isLiked: Boolean)
}