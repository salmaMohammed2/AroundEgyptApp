package com.example.aroundegypt.domain.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "recommended_experience_table")
data class RecommendedExperienceEntity(
    @PrimaryKey @ColumnInfo(name = "id") @SerializedName("id") val id: String,
    @ColumnInfo(name = "title") @SerializedName("title") val title: String? = null,
    @ColumnInfo(name = "recommended") @SerializedName("recommended") val recommended: Int? = null,
    @ColumnInfo(name = "description") @SerializedName("description") val description: String? = null,
    @ColumnInfo(name = "cover_photo") @SerializedName("cover_photo") val coverPhoto: String? = null,
    @ColumnInfo(name = "is_liked") @SerializedName("is_liked") var isLiked: Boolean? = null,
    @ColumnInfo(name = "likes_no") @SerializedName("likes_no") var likesNumber: Int? = null,
    @ColumnInfo(name = "views_no") @SerializedName("views_no") val viewsNumber: Int? = null,
    @ColumnInfo(name = "address") @SerializedName("address") val address: String? = null
) : Parcelable
