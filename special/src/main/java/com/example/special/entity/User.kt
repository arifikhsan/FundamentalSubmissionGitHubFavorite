package com.arifikhsan.githubfavorite.entity

import android.os.Parcelable
import com.arifikhsan.githubfavorite.config.Constant
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int = 0,
    val login: String = "",
    val name: String = "",
    val avatarUrl: String = "",
    val type: String = "",
    val bio: String? = "",
    val htmlUrl: String = "",
    val followers: Int = 0,
    val following: Int = 0,
    val publicRepos: Int = 0,
    val publicGists: Int = 0,
) :
    Parcelable