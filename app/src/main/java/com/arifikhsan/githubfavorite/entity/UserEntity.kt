package com.arifikhsan.githubfavorite.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val _id: Int,
    val id: Int,
    val login: String,
    val name: String,
    val avatarUrl: String,
    val type: String,
    val bio: String = "",
    val htmlUrl: String = "",
    val follower: Int = 0,
    val following: Int = 0,
    val publicRepos: Int = 0,
    val publicGists: Int = 0,
) :
    Parcelable