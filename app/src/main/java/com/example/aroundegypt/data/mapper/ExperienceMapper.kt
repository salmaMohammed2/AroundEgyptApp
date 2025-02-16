package com.example.aroundegypt.data.mapper

import com.example.aroundegypt.domain.entities.RecommendedExperienceEntity
import com.example.aroundegypt.domain.model.Experience

fun List<RecommendedExperienceEntity>.toExperienceList(): List<Experience> {
    return this.map { it.toExperience() }
}

fun RecommendedExperienceEntity.toExperience(): Experience {
    return Experience(
        id = this.id,
        title = this.title,
        address = this.address,
        recommended = this.recommended,
        description = this.description,
        coverPhoto = this.coverPhoto,
        isLiked = this.isLiked,
        likesNumber = this.likesNumber,
        viewsNumber = this.viewsNumber
    )
}

fun List<Experience>.toRecommendedExperienceList(): List<RecommendedExperienceEntity> {
    return this.map { it.toRecommendedExperienceEntity() }
}

fun Experience.toRecommendedExperienceEntity(): RecommendedExperienceEntity {
    return RecommendedExperienceEntity(
        id = this.id,
        title = this.title,
        address = this.address,
        recommended = this.recommended,
        description = this.description,
        coverPhoto = this.coverPhoto,
        isLiked = this.isLiked,
        likesNumber = this.likesNumber,
        viewsNumber = this.viewsNumber
    )
}