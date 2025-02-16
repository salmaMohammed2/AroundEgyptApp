package com.example.aroundegypt.di

import com.example.aroundegypt.data.local.ExperienceLocalDataSource
import com.example.aroundegypt.data.local.ExperienceLocalDataSourceImpl
import com.example.aroundegypt.data.remote.ExperienceRemoteDataSource
import com.example.aroundegypt.data.remote.ExperienceRemoteDataSourceImpl
import com.example.aroundegypt.data.repo.ExperienceDatabaseRepoImpl
import com.example.aroundegypt.data.repo.ExperienceRepoImpl
import com.example.aroundegypt.domain.repo.ExperienceDatabaseRepo
import com.example.aroundegypt.domain.repo.ExperienceRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ExperienceModule {

    @Binds
    abstract fun bindRemoteDataSource(experienceRemoteDataSourceImpl: ExperienceRemoteDataSourceImpl): ExperienceRemoteDataSource

    @Binds
    abstract fun bindRemoteRepository(experienceRepoImpl: ExperienceRepoImpl): ExperienceRepo

    @Binds
    abstract fun bindExperienceLocalDataSource(experienceLocalDataSourceImpl: ExperienceLocalDataSourceImpl): ExperienceLocalDataSource

    @Binds
    abstract fun bindExperienceLocalRepository(experienceDatabaseRepoImpl: ExperienceDatabaseRepoImpl): ExperienceDatabaseRepo

}