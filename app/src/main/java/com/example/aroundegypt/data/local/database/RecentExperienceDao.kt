package com.example.aroundegypt.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aroundegypt.domain.model.Experience

@Dao
interface RecentExperienceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecentExperiences(recentExperiences: List<Experience>)

    @Query("select * from recent_experience_table")
    fun getAllRecentExperiences(): List<Experience>

    @Query("DELETE from recent_experience_table")
    fun clearTable()

    @Query("UPDATE RECENT_EXPERIENCE_TABLE SET likes_no = :likes, is_liked = :isLiked WHERE id = :id")
    fun updateExperienceLikes(id: String, likes: Int, isLiked: Boolean)
}