package com.example.aroundegypt.di

import android.content.Context
import androidx.room.Room
import com.example.aroundegypt.data.local.database.ExperienceDatabase
import com.example.aroundegypt.data.local.database.RecentExperienceDao
import com.example.aroundegypt.data.local.database.RecommendedExperienceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExperienceDatabaseModule {

    @Singleton
    @Provides
    fun provideExperienceDatabase(@ApplicationContext context: Context?): ExperienceDatabase {
        return Room.databaseBuilder(
            context!!,
            ExperienceDatabase::class.java,
            "experience_database"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideRecentExperienceDao(recentExperienceDatabase: ExperienceDatabase): RecentExperienceDao {
        return recentExperienceDatabase.getRecentExperienceDao()
    }

    @Singleton
    @Provides
    fun provideRecommendedExperienceDao(recommendedExperienceDatabase: ExperienceDatabase): RecommendedExperienceDao {
        return recommendedExperienceDatabase.getRecommendedExperienceDao()
    }
}